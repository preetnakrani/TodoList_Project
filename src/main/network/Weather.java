package main.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weather {

    public Weather() {
        // do nothing
    }

    //EFFECTS: get the weather in json from the web and parses it to get description of weather and the temperature
    private void getWeather() throws MalformedURLException, IOException {
        BufferedReader br = null;
        PrintWriter writer = new PrintWriter("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/WeatherInfo.txt", "UTF-8");
        try {
            String vancouverweather = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca"
                    + "&APPID=ffc1c209c244ab054dff68fd56b0464c";
            String theURL = vancouverweather;
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            gettingWeather(br, writer, sb);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    private void gettingWeather(BufferedReader br, PrintWriter writer, StringBuilder sb) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        writer.println(sb);;
        writer.close();
    }

    private ArrayList<String> splitOnStuff(String line, String splitter) {
        String[] splits = line.split(splitter);
        return new ArrayList<>(Arrays.asList(splits));
    }

    //EFFECTS: returns string of the weather that gets using the helper
    public String printWeather() throws IOException {
        getWeather();
        List<String> lines = Files.readAllLines(Paths.get("/Users/preetnakrnai/IdeaProjects/TodoList_Project/data/WeatherInfo.txt"));
        ArrayList<String> parts = splitOnStuff(lines.get(0), "\"");
        ArrayList<String> parts1 = splitOnStuff(lines.get(0), "temp");
        String temp = parts1.get(1);
        String cut = temp.substring(2, 5);
        double result = Double.parseDouble(cut) - 273.15;
        double roundedResult = Math.round(result * 100.0) / 100.0;
        return parts.get(17) + " and the temperature is " + roundedResult + " celsius";
    }

}
