import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Nearly a total rewrite of last program turn in. I hope I am doing this correctly. CPSC_5133
 *
 * Main method will have a bunch of commented out methods that are self described. I understand I have more queries to
 * activate, but this is all I have right now.
 *
 *
 * @author  Luis Cortez
 * @version 0911192030
 */
public class HospitalDataJavaInterface implements TreatmentInterface, DeleteAllFace, PatientDoctorInterface, PersonInterface, TableUpdateInterface, RoomUtilization {
    public static void main(String[] args) throws IOException {
        HospitalDataJavaInterface app = new HospitalDataJavaInterface();

        // will delete all records from database
        app.deleteAllRecordsFromDataBase();

        // designate location of person data *.txt file
        String location = "D://personTableData.txt";
        // will read in all eligible 'person' data *.txt file into sqlite db
        app.readPersonTableData(location);

        // designate location of patientDoctorList data *.txt file
        String patientDoctorListLocation = "D:\\patientDoctorList.txt";
        // will read in all eligible 'patient_doctor_list' data *.txt file into sqlite db
        app.readPatientDoctorListData(patientDoctorListLocation);

        // designate location of treatment_table data *.txt file
        String treatmentDataListLocation = "D:\\treatmentData.txt";
        // will read in all eligible 'treatment_table' data *.txt file into sqlite db
        app.readTreatmentData(treatmentDataListLocation);

        // this interface will update all ancillary tables with data from person table
        app.tableUpdate();


//        app.roomUtilizationOne();
//        app.roomUtilizationTwo();
//        app.roomUtilizationThree();
//
//        app.patientInformationOne();
//        app.patientInformationTwo();
//        String s = "1995-12-31 01:01";
//        String e = "2020-12-31 01:01";

        java.sql.Timestamp startTime = java.sql.Timestamp.valueOf("1995-09-23 10:10:00");
        java.sql.Timestamp endTime = java.sql.Timestamp.valueOf("2020-09-23 10:10:00");


//        Timestamp start = new java.sql.Timestamp(new SimpleDateFormat("MM-dd-yyyy HH:mm:").parse(s).getTime());
//        Timestamp end = new java.sql.Timestamp(new SimpleDateFormat("MM-dd-yyyy HH:mm:").parse(e).getTime());



        //app.patientInformationThree(startTime, endTime);


    }




// this is just a test select sample method
//    /**
//     * Test sample selector... testing method.
//     */
//    private void selectSample(){
//        String sql = "SELECT person_first_name, person_last_name FROM person;";
//        try (Connection conn = this.connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)){
//            while (rs.next()) {
//                System.out.println(rs.getString("person_first_name") + "\t" +
//                        rs.getString("person_last_name") + "\t");
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }

    private void patientInformationOne() {
        String sql = "select  * from person where person_type = 'I' or person_type = 'O' ";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("First Name\t\t\t\t\t\t\tLast Name\t\t\t\t\t\tPatient ID\tEmergency Contact" +
                    "\t\t\t\t\t\tEmergency Number\tInsurance Number\t\t\t\tInsurance Company\t\t\t\tPrimary MD" +
                    "\t\t\t\t\t\t\tInitial DX\t\tAdmission Date\tDischarge Date");
            while (rs.next()) {
                System.out.println(rs.getString("person_first_name") + "\t\t\t\t" +
                        rs.getString("person_last_name") + "\t\t\t" +
                        rs.getInt("patient_id") + "\t\t\t" +
                        rs.getString("emergency_contact_name") + "\t\t" +
                        rs.getInt("emergency_contact_number") + "\t\t\t\t" +
                        rs.getString("insurance_policy_number") + "\t\t" +
                        rs.getString("insurance_policy_company") + "\t\t" +
                        rs.getString("patient_doctor_last_name") + "\t\t" +
                        rs.getString("initial_dx") + "\t\t" +
                        rs.getDate("admission_date") + "\t\t" +
                        rs.getDate("discharge_date"));
            }
            System.out.println("\nEnd of Report");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void patientInformationTwo() {

        String sql = "select * from person where person_type = 'I';";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("Patient ID\t\tPatient First Name \t\t\t\tPatient Last Name");
            while (rs.next()) {
                System.out.println(rs.getString("patient_id") + "\t\t\t\t" +
                        rs.getString("person_first_name") + "\t\t\t" +
                        rs.getString("person_last_name"));
            }
            System.out.println("\nEnd of Report");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void patientInformationThree(Timestamp startDate, Timestamp endDate) {
//
//        java.sql.Timestamp parsedStart = new java.sql.Timestamp(new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(startDate).getTime());
//        java.sql.Timestamp parsedEnd = new java.sql.Timestamp(new SimpleDateFormat("MM-dd-yyyy HH:mm").parse(endDate).getTime());
//
//        String sql = "select * from treatment_table where treatment_timestamp > " + parsedStart + " and  treatment_timestamp < " + parsedEnd + ";";
//        try (Connection conn = this.connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)){
//            System.out.println("Patient Last Name\t\tTreatment ID");
//            while (rs.next()) {
//                System.out.println(rs.getString("patient_last_name") + "\t\t\t\t" +
//                        rs.getString("treatment_list_key"));
//            }
//            System.out.println("\nEnd of Report");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    /**
     * Connection method to establish jbdc:sqlite connection.
     */
    Connection connect() {
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
