package ku.cs.network;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class LoginClient {
    public void login(String username, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            // HTTP Connection with json body
            HttpURLConnection httpURLConnection = Client.getClient().getHttpURLConnection(jsonObject.toString(), "/login");

            // Get Response JSON
            JSONObject responseJSON = Client.getClient().getResponseJSON(httpURLConnection);

            // save access_token
            String access_token = responseJSON.getString("access_token");

            Client.getClient().setAccessToken(access_token);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
