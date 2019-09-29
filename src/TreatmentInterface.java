import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public interface TreatmentInterface {
    default void readTreatmentData(String treatmentDataListLocation) throws IOException {
        String patient_last_name;
        String doctor_last_name;
        String treatment_type;
        String treatment_timestamp;

        BufferedReader buffReader = new BufferedReader(new FileReader(treatmentDataListLocation));
        String line;

        while ((line = buffReader.readLine()) != null) {
            String[] values = line.split(",", -1);
            patient_last_name = values[0];
            doctor_last_name = values[1];
            treatment_type = values[2];
            treatment_timestamp = values[3];

            treatmentListInsertMethod(patient_last_name,doctor_last_name,treatment_type,treatment_timestamp);
        }
        buffReader.close();
    }

    default void treatmentListInsertMethod(String patient_last_name, String doctor_last_name, String treatment_type, String treatment_timestamp) {
        String sql = "insert into treatment_table(patient_last_name, doctor_last_name, treatment_type, treatment_timestamp) values (?,?,?,?)";
        HospitalDataJavaInterface mainConnect = new HospitalDataJavaInterface();

        try (Connection conn = mainConnect.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,patient_last_name);
            ps.setString(2,doctor_last_name);
            ps.setString(3,treatment_type);
//            Timestamp sqlTimeStamp = new java.sql.Timestamp(new SimpleDateFormat("MM-dd-yyyy HH:mm").parse(treatment_timestamp).getTime());
            java.sql.Timestamp treatTime = new java.sql.Timestamp(new SimpleDateFormat("MM-dd-yyyy HH:mm").parse(treatment_timestamp).getTime());
            ps.setTimestamp(4, treatTime);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
