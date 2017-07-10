import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Weather {
    public String getWeather(String city) throws IOException {
        String endpoint = "https://query.yahooapis.com/v1/public/yql?q=select%20item%20from" +
                "%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%" +
                "20where%20text%3D%22" + URLEncoder.encode(city, "UTF-8") + "%2C%20com%22)&format=json&env=store%3A%2F%" +
                "2Fdatatables.org%2Falltableswithkeys";
        URL url = new URL(endpoint);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
        JsonParser jp = new JsonParser();
        JsonObject item = jp.parse(new InputStreamReader((InputStream) urlConnection.getContent()))
                .getAsJsonObject().get("query")
                .getAsJsonObject().get("results")
                .getAsJsonObject().get("channel")
                .getAsJsonObject().get("item").getAsJsonObject();
        JsonObject condition1 = item.get("condition").getAsJsonObject();
        String location = item.get("title").getAsString();
        String resultLocation = location.substring(location.indexOf("Conditions"), location.indexOf("at"));
        String date = condition1.get("date").getAsString();
        String resultDate = date.substring(0, 17);
        int tempF = condition1.get("temp").getAsInt();
        String text = condition1.get("text").getAsString();
        int temp = ((tempF - 32) * 5) / 9;
        String result = (resultLocation + "\n" + resultDate + "\n" +
                "Temperature " + temp + "°" + ", " + text);
        return result;
    }
}

