import java.sql.Connection;
import java.sql.Statement;

public interface TableUpdateInterface {
    /**
     * Table update method. Will distribute all data from person table to all other tables.
     */
    default void tableUpdate(){
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

        HospitalDataJavaInterface app = new HospitalDataJavaInterface();
        try (Connection conn = app.connect();
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
