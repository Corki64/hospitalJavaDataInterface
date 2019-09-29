import java.sql.Connection;
import java.sql.Statement;

/**
 * Interface class that will delete all data from hospital.db database tables.
 *
 * @version 0928192110
 */
public interface DeleteAllFace {
    default void deleteAllRecordsFromDataBase(){
        // sql declaration statement
        String deleteAllRecords = "delete from administrator; delete from doctor; " +
                "delete from in_patient; delete from nurse; delete from out_patient; delete from patient; " +
                "delete from patient_doctor_list; delete from technician; " +
                "delete from treatment_table; delete from volunteer; delete from employee; delete from person;";

        // pulling up an instance of HospitalDataJavaInterface to access the sql connection method
        HospitalDataJavaInterface app = new HospitalDataJavaInterface();

        try (Connection conn = app.connect();
             Statement stmt = conn.createStatement()
        ){
            stmt.executeUpdate(deleteAllRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
