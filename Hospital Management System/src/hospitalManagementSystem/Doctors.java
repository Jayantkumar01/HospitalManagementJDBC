package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection connection;

    public  Doctors(Connection connection) {
        this.connection = connection;
    }
    public void viewDoctors() {
        String sql = "select * from doctors";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            System.out.println("Doctors :");
            System.out.println(resultSet.getInt("id"));
            System.out.println("Name: " + resultSet.getString("name"));
            System.out.println("Specialization: " + resultSet.getString("specialization"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getDoctorsByID(int id) {
        String sql = "Select * from doctors where id = ? ";
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
}
