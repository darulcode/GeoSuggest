package git.darul.geocitysuggest.utils;

import git.darul.geocitysuggest.entity.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileLoader {

    public static List<City> loadCityFromCSV(String filePath) {
        List<City> cities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                FileLoader.class.getResourceAsStream(filePath)))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split("\t");

                try {
                    Integer id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double latitude = Double.parseDouble(parts[4]);
                    double longitude = Double.parseDouble(parts[5]);
                    String country = parts[8];
                    String admin1 = parts[10];

                    cities.add(new City(id, name, latitude, longitude, country, admin1));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return cities;
    }

}
