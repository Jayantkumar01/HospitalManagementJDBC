package hospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class hospitalManagementSystem {
    private static final String url = "jdbc:postgresql://localhost:5432/hospital";
    private static final String user = "postgres";
    private static final String password = "hehehe";

    public static void main(String[] args) {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            Patient patient = new Patient(connection,scanner);
            Doctors doctors = new Doctors(connection);
            while(true) {
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. view Patient");
                System.out.println("3. Delete Patient");
                System.out.println("4. view Doctor");
                System.out.println("5. Book Appointment");
                System.out.println("6. Show appointments");
                System.out.println("7. exit");
                int choice = scanner.nextInt();


                switch (choice) {
                    case 1:
                        //
                        patient.addpatient();
                        System.out.println();
                        break;
                    case 2:
                        //
                        patient.viewpatient();
                        break;
                    case 3:
                        //
                        patient.deletepatient(connection);
                        break;
                    case 4:
                        doctors.viewDoctors();
                        break;
                    case 5:
                        //
                        bookAppointment(patient,doctors,connection,scanner);
                        break;
                    case 6:
                        showAppointments(connection);
                    case 7:
                        return;
                    default:
                        System.out.println("Enter valid choice");
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    public static void bookAppointment(Patient patient,Doctors doctors,Connection connection,Scanner scanner){
        System.out.println("Enter Patient Id: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor Id: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter Appointment date(YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        java.sql.Date sqlDate = java.sql.Date.valueOf(appointmentDate);
        if(patient.getPatientsByID(patientId) && doctors.getDoctorsByID(doctorId)){
            if(checkDoctorAvailablity(doctorId,sqlDate,connection)){
                String appointmentQuery = "insert into appointments(patients_id,doctor_id,appointment_date) values(?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setDate(3, sqlDate);
                    preparedStatement.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor is not available on this date");
            }
        } else {
            System.out.println("Patient does not exist");
        }
    }
    public static boolean checkDoctorAvailablity(int doctorId,Date appointmentDate,Connection connection){
        String query = "Select count(*) from appointments where doctor_id=? and appointment_date=?";
        try {
            PreparedStatement preparedStatement  = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setDate(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void showAppointments(Connection connection){
        String query = "SELECT patients.name AS patient_name, doctors.name AS doctor_name, appointments.id AS appointment_id, appointments.appointment_date FROM appointments JOIN patients ON appointments.patients_id = patients.id JOIN doctors ON appointments.doctor_id = doctors.id;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("appointments");
            while(resultSet.next()){
                int appointmentId = resultSet.getInt( 3);
                String patient_name = resultSet.getString(1);
                String doctor_name  = resultSet.getString(2);
                String appointmentDate = resultSet.getString(4);
                System.out.println(appointmentId+" "+patient_name+" "+doctor_name+" "+appointmentDate);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
