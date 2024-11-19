package ku.cs.connect;

import ku.cs.models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeProfileConnect {

    public Employee employeeProfile() {
        Connection connection = DatabaseConnect.getConnection();
        String employeeId = LoginConnect.getCurrentUser().getID(); // ใช้ ID จาก currentUser ที่ล็อกอิน
        String query = "SELECT Employee_Name, Employee_Address, Employee_Phone_number FROM employee WHERE Employee_ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employeeId); // ใช้ Employee_ID ของพนักงานที่ล็อกอินอยู่

            ResultSet resultSet = statement.executeQuery();
            Employee employee = new Employee();
            if (resultSet.next()) {
                employee.setName(resultSet.getString("Employee_Name"));
                employee.setPhoneNumber(resultSet.getString("Employee_Phone_number"));
                employee.setAddress(resultSet.getString("Employee_Address"));
            }
            return employee;
        } catch (SQLException e) {
            System.err.println("Failed to load employee data: " + e.getMessage());
        } finally {
            DatabaseConnect.closeConnection();
        }
        return null;
    }
}
