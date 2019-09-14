import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;

/**
 * Nearly a total rewrite of last program turn in. I hope I am doing this correctly. CPSC_5133
 *
 *
 * @author  Luis Cortez
 * @version 0911192030
 */
public class HospitalDataJavaInterface {
    public static void main(String[] args) throws IOException {
        HospitalDataJavaInterface app = new HospitalDataJavaInterface();
        String location = "D://personTableData.txt";
        app.readPersonTableData(location);
    }

    /**
     * This method will  read the data from the text file and call our personTableInsertMethod.
     * @param location - file location of *.txt
     * @throws IOException - for file input exception
     */
    private void readPersonTableData(String location) throws IOException {
        // declare all variables for prepared statement
        String person_type;
        String person_first_name = "";
        String person_last_name = "";
        String doctor_privilege = "";
        String patient_id;
        String room_id = "";
        String emergency_contact_name = "";
        String emergency_contact_number;
        String insurance_policy_number = "";
        String insurance_policy_company = "";
        String patient_doctor_last_name = "";
        String initial_dx = "";
        String admission_date = null;
        String discharge_date = null;

        // implement buffered reader for text file
        BufferedReader buffReader = new BufferedReader(new FileReader(location));
        String line;

        while ((line = buffReader.readLine()) != null) {
            String[] values = line.split(",",-1);
            person_type = values[0];
            person_first_name = values[1];
            person_last_name = values[2];
            doctor_privilege = values[3];
            patient_id = values[4];
            room_id = values[5];
            emergency_contact_name = values[6];
            emergency_contact_number = values[7];
            insurance_policy_number = values[8];
            insurance_policy_company = values[9];
            patient_doctor_last_name = values[10];
            initial_dx = values[11];
            admission_date = values[12];
            discharge_date = values[13];

            personTableInsertMethod(person_type, person_first_name, person_last_name, doctor_privilege, patient_id,
                    room_id, emergency_contact_name, emergency_contact_number, insurance_policy_number,
                    insurance_policy_company, patient_doctor_last_name, initial_dx, admission_date, discharge_date);
        }
        buffReader.close();
    }

    /**
     * This method will accept arguments from readPersonTableData method and connect to the database and insert
     * values via a prepared statement.
     * @param person_type  - values received from *.txt
     * @param person_first_name  - values received from *.txt
     * @param person_last_name  - values received from *.txt
     * @param doctor_privilege  - values received from *.txt
     * @param patient_id  - values received from *.txt
     * @param room_id  - values received from *.txt
     * @param emergency_contact_name  - values received from *.txt
     * @param emergency_contact_number  - values received from *.txt
     * @param insurance_policy_number  - values received from *.txt
     * @param insurance_policy_company  - values received from *.txt
     * @param patient_doctor_last_name  - values received from *.txt
     * @param initial_dx  - values received from *.txt
     * @param admission_date  - values received from *.txt
     * @param discharge_date  - values received from *.txt
     */
    private void personTableInsertMethod(String person_type, String person_first_name, String person_last_name,
                                         String doctor_privilege, String patient_id, String room_id,
                                         String emergency_contact_name, String emergency_contact_number,
                                         String insurance_policy_number, String insurance_policy_company,
                                         String patient_doctor_last_name, String initial_dx, String admission_date,
                                         String discharge_date) {
        String sql = "insert into person(person_type,person_first_name,person_last_name,doctor_privilege,patient_id," +
                "room_id,emergency_contact_name,emergency_contact_number,insurance_policy_number," +
                "insurance_policy_company,patient_doctor_last_name,initial_dx,admission_date,discharge_date)" +
                " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try(Connection conn = this.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,person_type);
            ps.setString(2,person_first_name);
            ps.setString(3,person_last_name);
            ps.setString(4,doctor_privilege);
            try {
                if(patient_id != null) {
                    ps.setInt(5, Integer.parseInt(patient_id));
                }
            } catch (NumberFormatException e) {
                ps.setNull(5, Types.INTEGER);
            }
            try {
                if(patient_id != null) {
                    ps.setInt(6, Integer.parseInt(room_id));
                }
            } catch (NumberFormatException e) {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setString(7,emergency_contact_name);
            try {
                if(emergency_contact_number != null) {
                    ps.setInt(8, Integer.parseInt(emergency_contact_number));
                }
            } catch (NumberFormatException e) {
                //int tempInt = 0;
                ps.setNull(8, Types.INTEGER);
            }
            ps.setString(9,insurance_policy_number);
            ps.setString(10,insurance_policy_company);
            ps.setString(11,patient_doctor_last_name);
            ps.setString(12,initial_dx);
            try {
                if (admission_date != null) {
                    Date inDate = new java.sql.Date(((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(admission_date)).getTime());
                    ps.setDate(13,inDate);
                }
            } catch (DateTimeException | ParseException e) {
                ps.setNull(13, Types.DATE);
            }
            try {
                if (discharge_date != null) {
                    Date disDate = new java.sql.Date(((java.util.Date) new SimpleDateFormat("yyyy-MM-dd").parse(discharge_date)).getTime());
                    ps.setDate(14,disDate);
                }
            } catch (DateTimeException | ParseException e) {
                ps.setNull(14, Types.DATE);
            }
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        /**
         * Connection method to establish jbdc:sqlite connection.
         */
        private Connection connect () {
            //SQLite connection string
            String url = "jdbc:sqlite:D:/___finalVersion/final.db/hospital.db";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
                //turn on FOREIGN KEY enforcement
                conn.createStatement().executeUpdate("PRAGMA FOREIGN_KEYS = ON");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return conn;
        }
}
