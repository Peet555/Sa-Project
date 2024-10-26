package ku.cs.network;
import org.json.JSONObject;

import java.net.HttpURLConnection;
public class RegisterClient {

    public String registerCustomer(String username, String name,  String password) throws Exception {

        JSONObject jsonObject = toJSONObject(username, name,  password);

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/register_customer");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);
        return response.toString();

    }

    public String registerEmployee(String username, String name,  String password,String role) throws Exception {

        JSONObject jsonObject = toJSONObjectEmployee(username, name,  password,role);

        HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/register_employee");

        JSONObject response;
        response = Client.getClient().getResponseJSON(httpURLConnection);
        return response.toString();

    }

    private JSONObject toJSONObject(String username, String name,  String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("name", name);
        jsonObject.put("password", password);
        return jsonObject;
    }

    private JSONObject toJSONObjectEmployee(String username, String name,  String password,String role) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("name", name);
        jsonObject.put("password", password);
        jsonObject.put("role", role);
        return jsonObject;
    }
}
