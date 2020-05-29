package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherApi {
    //ecf5b97597a9fcd5af1f7b370c62b477
    public static String getWeather(String message, Weather weather) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=ecf5b97597a9fcd5af1f7b370c62b477");
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        weather.setName(object.getString("name"));

        JSONObject mainObj = object.getJSONObject("main");
        weather.setTemp(mainObj.getDouble("temp"));
        weather.setHumidity(mainObj.getDouble("humidity"));

        JSONArray jsonArray = object.getJSONArray("weather");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object1 = jsonArray.getJSONObject(i);
            weather.setIcon((String) object1.get("icon"));
            weather.setMain((String) object1.get("main"));
        }
        return "Місто " + weather.getName() + "\n" +
                "Температура " + weather.getTemp() + "C\n" +
                "Вологість " + weather.getHumidity() + "\n" +
                "Температура " + weather.getTemp() + "%\n" +
                "Опис " + weather.getMain() + "\n" +
                "http://openweathermap.org/img/wn/" + weather.getIcon() + ".png";
    }
}
