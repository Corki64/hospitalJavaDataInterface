import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public interface PatientDoctorInterface {
    default void readPatientDoctorListData(String patientDoctorListLocation) throws IOException {
        String patient_last_name;
        String doctor_last_name;

        BufferedReader buffReader = new BufferedReader(new FileReader(patientDoctorListLocation));
        String line;

        while ((line = buffReader.readLine()) != null) {
            String[] values = line.split(",", -1);
            patient_last_name = values[0];
            doctor_last_name = values[1];

            patientDoctorListInsertMethod(patient_last_name, doctor_last_name);
        }
        buffReader.close();
    }

    default void patientDoctorListInsertMethod(String patient_last_name, String doctor_last_name) {
        String sql = "insert into patient_doctor_list(patient_name, doctor_name) values (?,?)";
        HospitalDataJavaInterface app = new HospitalDataJavaInterface();
        try (Connection conn = app.connect()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, patient_last_name);
            ps.setString(2, doctor_last_name);
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
