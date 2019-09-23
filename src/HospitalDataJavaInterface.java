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


//        app.deleteAllRecordsFromDataBase();
//
//        String location = "D://personTableData.txt";
//        app.readPersonTableData(location);
//
//
//        String patientDoctorListLocation = "D:\\patientDoctorList.txt";
//        app.readPatientDoctorListData(patientDoctorListLocation);
//
//        String treatmentDataListLocation = "D:\\treatmentData.txt";
//        app.readTreatmentData(treatmentDataListLocation);
//        app.tableUpdate();


//        app.roomUtilizationOne();
//        app.roomUtilizationTwo();
//        app.roomUtilizationThree();

//        app.patientInformationOne();
//        app.patientInformationTwo();
        String s = "1995-12-31 01:01:01";

        //yyyy-mm-dd hh:mm:ss
        String e = "2020-12-31 01:01:01";
            Timestamp startDate = Timestamp.valueOf(s);
            Timestamp endDate = Timestamp.valueOf(e);

            app.patientInformationThree(startDate, endDate);
    }


    private void patientInformation() {
    }

    private void readTreatmentData(String treatmentDataListLocation) throws IOException {
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

    private void treatmentListInsertMethod(String patient_last_name, String doctor_last_name, String treatment_type, String treatment_timestamp) {
        String sql = "insert into treatment_table(patient_last_name, doctor_last_name, treatment_type, treatment_timestamp) values (?,?,?,?)";
        try (Connection conn = this.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,patient_last_name);
            ps.setString(2,doctor_last_name);
            ps.setString(3,treatment_type);
            Timestamp sqlTimeStamp = new java.sql.Timestamp(new SimpleDateFormat("MM-dd-yyyy HH:mm").parse(treatment_timestamp).getTime());
            ps.setTimestamp(4, sqlTimeStamp);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readPatientDoctorListData(String patientDoctorListLocation) throws IOException {
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

    private void patientDoctorListInsertMethod(String patient_last_name, String doctor_last_name) {
        String sql = "insert into patient_doctor_list(patient_name, doctor_name) values (?,?)";
        try (Connection conn = this.connect()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, patient_last_name);
            ps.setString(2, doctor_last_name);
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will  read the data from the text file and call our personTableInsertMethod.
     * @param location - file location of *.txt
     * @throws IOException - for file input exception
     */
    private void readPersonTableData(String location) throws IOException {
        // declare all variables for prepared statement
        String person_type;
        String person_first_name;
        String person_last_name;
        String doctor_privilege;
        String patient_id;
        String room_id;
        String emergency_contact_name;
        String emergency_contact_number;
        String insurance_policy_number;
        String insurance_policy_company;
        String patient_doctor_last_name;
        String initial_dx;
        String admission_date;
        String discharge_date;
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
                    Date inDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(admission_date).getTime());
                    ps.setDate(13,inDate);
                }
            } catch (DateTimeException | ParseException e) {
                ps.setNull(13, Types.DATE);
            }
            try {
                if (discharge_date != null) {
                    Date disDate = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(discharge_date).getTime());
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

    /**
     * Test sample selector... testing method.
     */
    private void selectSample(){
        String sql = "SELECT person_first_name, person_last_name FROM person;";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println(rs.getString("person_first_name") + "\t" +
                        rs.getString("person_last_name") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void roomUtilizationOne(){
        String sql = "select  room_id, person_first_name, person_last_name, admission_date from person where room_id IS NOT NULL";
        try (Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("Rooms Number \tPatient First Name \t\t\t\tPatient Last Name \t\t\t\tAdmission Date");
            while (rs.next()) {
                System.out.println(rs.getString("room_id") + "\t\t\t\t" +
                        rs.getString("person_first_name") + "\t\t\t" +
                        rs.getString("person_last_name") + "\t\t\t" +
                        rs.getDate("admission_date"));
            }
            System.out.println("\nEnd of Report");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void roomUtilizationTwo(){
        String sql = "select  room_id from person where person_last_name is null";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("Rooms Available");
            while (rs.next()) {
                System.out.println(rs.getString("room_id") + "\t");
            }
            System.out.println("\nEnd of Report");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void roomUtilizationThree() {
        String sql = "select  room_id, patient_first_name, patient_last_name, admission_date from rooms where patient_last_name is not null ";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("Rooms Number \tPatient First Name \t\t\t\tPatient Last Name \t\t\t\tAdmission Date ");
            while (rs.next()) {
                System.out.println(rs.getString("room_id") + "\t\t\t\t" +
                        rs.getString("patient_first_name") + "\t\t\t" +
                        rs.getString("patient_last_name") + "\t\t\t" +
                        rs.getDate("admission_date"));
            }
            System.out.println("\nEnd of Report");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    private void patientInformationThree(Timestamp startDate, Timestamp endDate) {

        String sql = "select * from treatment_table where treatment_timestamp between '" + startDate + "' and '" + endDate + "';";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            System.out.println("Patient Last Name\t\tTreatment ID");
            while (rs.next()) {
                System.out.println(rs.getString("patient_last_name") + "\t\t\t\t" +
                        rs.getString("treatment_list_key"));
            }
            System.out.println("\nEnd of Report");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void deleteAllRecordsFromDataBase(){
        String deleteAllRecords = "delete from administrator; delete from doctor; " +
                "delete from in_patient; delete from nurse; delete from out_patient; delete from patient; " +
                "delete from patient_doctor_list; delete from technician; " +
                "delete from treatment_table; delete from volunteer; delete from employee; delete from person;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()
        ){
            stmt.executeUpdate(deleteAllRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Table update method. Wil distribute all data from person table to all other tables.
     */
    private void tableUpdate(){
        String patientUpdate = "insert into patient(person_id, patient_id, patient_type) " +
                "select person_id, patient_id, person_type from person " +
                "where person_type = 'I' or person_type = 'O';";
        String outPatientUpdate = "insert into out_patient(patient_id, person_type, person_first_name, " +
                "person_last_name) select patient_id, person_type, person_first_name, person_last_name from person " +
                "where person_type = 'O'";
        String inPatientUpdate = "insert into in_patient(patient_id, person_type, room_id, emergency_contact_name, " +
                "emergency_contact_number, insurance_policy_number, insurance_policy_company, " +
                "patient_doctor_last_name, initial_dx, admission_date, discharge_date) select patient_id," +
                " person_type, room_id, emergency_contact_name, emergency_contact_number, insurance_policy_number," +
                " insurance_policy_company, patient_doctor_last_name, initial_dx, admission_date, discharge_date " +
                "from person where person_type = 'I' or emergency_contact_name = not null " +
                "and emergency_contact_number = not null and insurance_policy_number = not null " +
                "and insurance_policy_company = not null and patient_doctor_last_name = not null;";
        String employeeUpdate = "insert into employee(employee_first_name, employee_last_name, person_id," +
                " employee_type, doctor_priv) " +
                "select person_first_name, person_last_name, person_id, person_type, doctor_privilege from person " +
                "where person_type = 'V' or person_type = 'D' or person_type = 'A' or person_type = 'N' " +
                "or person_type = 'T';";
        String volunteerUpdate = "insert into volunteer(person_first_name,person_last_name,person_id, employee_id) " +
                "select employee_first_name, employee_last_name, person_id, employee_id from employee " +
                "where employee_type = 'V';";
        String doctorUpdate = "insert into doctor(person_first_name,person_last_name,person_id, " +
                "employee_id, doctor_priv) " +
                "select employee_first_name, employee_last_name, person_id, employee_id, doctor_priv from employee " +
                "where employee_type = 'D' ";
        String administratorUpdate = "insert into administrator(person_first_name,person_last_name,person_id," +
                " employee_id) select employee_first_name, employee_last_name, person_id, employee_id from employee " +
                "where employee_type = 'A';";
        String nurseUpdate = "insert into nurse(person_first_name,person_last_name,person_id, employee_id) " +
                "select employee_first_name, employee_last_name, person_id, employee_id from employee " +
                "where employee_type = 'N';";
        String technicianUpdate = "insert into technician(person_first_name,person_last_name,person_id, employee_id) " +
                "select employee_first_name, employee_last_name, person_id, employee_id from employee " +
                "where employee_type = 'T';";
        String roomUpdate = "insert into rooms(room_id, patient_first_name, patient_last_name, patient_id, person_id," +
                " admission_date) " +
                "select room_id, person_first_name, person_last_name, patient_id, person_id, admission_date " +
                "from person " +
                "where person_type = 'I';";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()
        ){
            stmt.executeUpdate(patientUpdate);
            stmt.executeUpdate(outPatientUpdate);
            stmt.executeUpdate(inPatientUpdate);
            stmt.executeUpdate(employeeUpdate);
            stmt.executeUpdate(volunteerUpdate);
            stmt.executeUpdate(doctorUpdate);
            stmt.executeUpdate(administratorUpdate);
            stmt.executeUpdate(nurseUpdate);
            stmt.executeUpdate(technicianUpdate);
            stmt.executeUpdate(roomUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }














}
