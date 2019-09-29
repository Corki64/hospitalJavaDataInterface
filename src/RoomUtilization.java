import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface RoomUtilization {
    default void roomUtilizationOne(){
        String sql = "select  room_id, person_first_name, person_last_name, admission_date from person where room_id IS NOT NULL";
        HospitalDataJavaInterface app = new HospitalDataJavaInterface();
        try (Connection conn = app.connect();
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

    default void roomUtilizationTwo(){
        String sql = "select  room_id from person where person_last_name is null";
        HospitalDataJavaInterface app = new HospitalDataJavaInterface();
        try (Connection conn = app.connect();
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

    default void roomUtilizationThree() {
        String sql = "select  room_id, patient_first_name, patient_last_name, admission_date from rooms where patient_last_name is not null ";
        HospitalDataJavaInterface app = new HospitalDataJavaInterface();
        try (Connection conn = app.connect();
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
}
