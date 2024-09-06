package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
        private Connection connection;
        private Scanner scanner;

        public Patient(Connection connection, Scanner scanner) {
            this.connection = connection;
            this.scanner = scanner;
        }
        public void addpatient() {
            System.out.println("Enter the name of the patient");
            String name = scanner.nextLine();
            String name2 = scanner.nextLine();
            String name3 = name+" "+name2;
            System.out.println("Enter the age of the patient");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter the gender of the patient");
            String gender = scanner.nextLine();

            try{
                String sql = "insert into patients(name,age,gender) VALUES (?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name3);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, gender);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Patient added successfully");
                } else {
                    System.out.println("Patient not added");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        public void viewpatient() {
            String sql = "select * from patients";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("patients :");
                while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("gender"));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            public boolean getPatientsByID(int id) {
                String sql = "Select * from patients where id = ? ";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        return true;
                    } else {
                        return false;
                }}
                catch (SQLException e) {
                    e.printStackTrace();
                }
                return false;
        }
        public void deletepatient(Connection connection) {
            System.out.println("Enter the id of the patient");
            int id = scanner.nextInt();
            String sql = "Delete from patients where id = ?";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Patient deleted successfully");
                } else {
                    System.out.println("Patient not deleted");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
}
