import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Weather {

  /**
   * Получение сводки погоды с yahoo weather
   *
   * @param city Название города, получаем из сообщения пользователя в классе App(метод getCity) при
   * помощи работы со строками
   * @return Готовый текст сообщения для отправки пользователю, содержащий сведения о погоде в
   * заданном городе
   */
  public String getWeather(String city) throws IOException {
    String endpoint = "https://query.yahooapis.com/v1/public/yql?q=select%20item%20from" +
        "%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%" +
        "20where%20text%3D%22" + URLEncoder.encode(city, "UTF-8")
        + "%2C%20com%22)&format=json&env=store%3A%2F%" +
        "2Fdatatables.org%2Falltableswithkeys";
    URL url = new URL(endpoint);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.connect();
    JsonParser jp = new JsonParser();
    InputStream inputStream = (InputStream) urlConnection.getContent();
    JsonElement item1 = jp.parse(new InputStreamReader(inputStream))
        .getAsJsonObject().get("query")
        .getAsJsonObject().get("results");
    if (!(item1 instanceof JsonNull)) {
      JsonObject item_result = item1.getAsJsonObject().get("channel").getAsJsonObject().get("item")
          .getAsJsonObject();
      JsonObject condition1 = item_result.get("condition").getAsJsonObject();
      String location = item_result.get("title").getAsString();
      String resultLocation = location
          .substring(location.indexOf("Conditions"), location.indexOf("at"));
      String date = condition1.get("date").getAsString();
      String resultDate = date.substring(0, 17);
      int tempF = condition1.get("temp").getAsInt();
      String text = condition1.get("text").getAsString();
      Weather weather = new Weather();
      return (resultLocation + "\n" + resultDate + "\n" +
          "Temperature " + weather.FtoC(tempF) + "°" + ", " + text);
    } else {

      return "Запрашиваемый город не найден, попробуйте ввести правильное название.";

    }
  }

  /**
   * Фаренгейт в Цельсий
   *
   * @param tempF Температура в Фаренгейтах
   * @return Температура в Цельсиях
   */
  public int FtoC(int tempF) {
    return ((tempF - 32) * 5) / 9;
  }
}


