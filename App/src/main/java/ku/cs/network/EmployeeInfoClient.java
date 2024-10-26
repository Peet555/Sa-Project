package ku.cs.network;

import ku.cs.models.Customer;
import ku.cs.models.Employee;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class EmployeeInfoClient {

    public Employee getEmployee() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/userinfo");

            // Get Response JSON
            JSONObject userInfo = Client.getClient().getResponseJSON(httpURLConnection);

            Employee employee = new Employee();
            employee.setID(userInfo.getString("id"));
            employee.setName(userInfo.getString("name"));
            employee.setAddress(userInfo.getString("address"));
            employee.setPassword(userInfo.getString("password"));
            employee.setPhoneNumber(userInfo.getString("phoneNumber"));
            employee.setUsername(userInfo.getString("username"));
            employee.setRole(userInfo.getString("role"));

            return employee;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
