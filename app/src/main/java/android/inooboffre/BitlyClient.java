package android.inooboffre;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BitlyClient {
    public String BitlyConnection(String promoCodeApplied, String ApiToken) {
        try {
            OkHttpClient client = new OkHttpClient();
            // Send the link to shorten to Bitly servers.
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            String JsonRequest = "{ \"long_url\": \"" + promoCodeApplied + "\" }";
            RequestBody formBody = RequestBody.create(JSON, JsonRequest);
            // Get the Bitly API key
            String AuthorizationGet = "Bearer " + ApiToken;
            // Create request
            Request request = new Request.Builder()
                    .url("https://api-ssl.bitly.com/v4/shorten")
                    .addHeader("Content-Type", "application/json; utf-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", AuthorizationGet)
                    .post(formBody)
                    .build();
            // Get response from server
            Response response = client.newCall(request).execute();
            String getResponse = response.body().string();
            String ShortenedAmazonLink = getResponse.substring(getResponse.indexOf("\"link\":\""));

            ShortenedAmazonLink = ShortenedAmazonLink.replace("\"link\":\"", "");
            ShortenedAmazonLink = ShortenedAmazonLink.substring(0, ShortenedAmazonLink.indexOf("\""));
            return ShortenedAmazonLink;
        } catch (Exception e) {
            e.printStackTrace();
            return "unvalid :(";
        }

    }
}
