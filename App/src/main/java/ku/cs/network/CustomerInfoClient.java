package ku.cs.network;

import ku.cs.models.Customer;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
public class CustomerInfoClient {

    public Customer getCustomerInfo() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("access_token", Client.getClient().getAccessToken());

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/userinfo");

            // Get Response JSON
            JSONObject userInfo = Client.getClient().getResponseJSON(httpURLConnection);

            Customer customer = new Customer();
            customer.setID(userInfo.getString("id"));
            customer.setUsername(userInfo.getString("username"));
            customer.setName(userInfo.getString("name"));
            customer.setEmail(userInfo.getString("email"));
            customer.setPassword(userInfo.getString("password"));
            customer.setAddress(userInfo.getString("address"));
            customer.setPhoneNumber(userInfo.getString("phoneNumber"));

            return customer;

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
