package chATbot.weatherApi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WeatherApiGUI extends JFrame {
    private JLabel temperatureLabel;
    private JButton checkButton;
    private JTextField cityField;

    public WeatherApiGUI() {
        setTitle("Weather API");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);

        // create components
        JLabel cityLabel = new JLabel("City:");
        cityField = new JTextField();
        temperatureLabel = new JLabel("Temperature:");
        checkButton = new JButton("Check Weather");
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkWeather();
            }
        });

        // add components to frame
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(new JLabel());
        panel.add(checkButton);
        panel.add(temperatureLabel);
        add(panel);

        setVisible(true);
    }

    private void checkWeather() {
        try {
            String city = cityField.getText();
            if (city.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter a city name.");
                return;
            }

            // Set the API endpoint URL and your API key
            String endpointUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=30fc7ee828b5d4f90ce78df1ac686aca&units=metric";

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

            // Determine clothing suggestion based on the temperature
            String clothingSuggestion;
            if (temperature < 10) {
                clothingSuggestion = "It's cold, wear a jacket and scarf.";
            } else if (temperature < 20) {
                clothingSuggestion = "It's cool, wear a light jacket or sweater.";
            } else {
                clothingSuggestion = "It's warm, wear a t-shirt and shorts.";
            }

            // Display the temperature and clothing suggestion on the GUI
            temperatureLabel.setText("Temperature in " + city + ": " + temperature + " °C. " + clothingSuggestion);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WeatherApiGUI weatherApiGUI = new WeatherApiGUI();
    }
}
