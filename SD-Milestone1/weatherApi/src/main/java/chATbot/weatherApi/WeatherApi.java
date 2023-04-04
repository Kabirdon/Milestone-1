package chATbot.weatherApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherApi {
    public static void main(String[] args) {
        try {
            // Set the API endpoint URL and your API key
            String endpointUrl = "https://api.openweathermap.org/data/2.5/weather?q=cork&appid=30fc7ee828b5d4f90ce78df1ac686aca&units=metric";
            
            // Create a new HTTP connection to the endpoint URL
            URL url = new URL(endpointUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            // Read the response from the API endpoint
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            // Parse the JSON response into a Java object
            JSONObject jsonObject = new JSONObject(response.toString());
            
            // Extract the temperature from the API response
            double temperature = jsonObject.getJSONObject("main").getDouble("temp");
            
           System.out.println(temperature);
           
      
            // Determine clothing suggestion based on the temperature
            String clothingSuggestion;
            if (temperature < 10) {
                clothingSuggestion = "It's cold, wear a jacket and scarf.";
            } else if (temperature < 20) {
                clothingSuggestion = "It's cool, wear a light jacket or sweater.";
            } else {
                clothingSuggestion = "It's warm, wear a t-shirt and shorts.";
            }
            
            // Print the clothing suggestion to the console
            System.out.println(clothingSuggestion);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
