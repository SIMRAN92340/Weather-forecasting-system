package com.weatherforecast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/WeatherForecastDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // Establish database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Save weather data into the database
    public void saveWeatherData(String city, double temperature, int humidity, String condition) {
        String query = "INSERT INTO weather_data (city_name, temperature, humidity, condition) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, city);
            stmt.setDouble(2, temperature);
            stmt.setInt(3, humidity);
            stmt.setString(4, condition);
            stmt.executeUpdate();
            System.out.println("Weather data saved successfully!");
        } catch (SQLException e) {
            System.err.println("Error saving weather data: " + e.getMessage());
        }
    }

    // Example of fetching weather data
    public void getWeatherData(String city) {
        String query = "SELECT * FROM weather_data WHERE city_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("City: " + rs.getString("city_name"));
                System.out.println("Temperature: " + rs.getDouble("temperature"));
                System.out.println("Humidity: " + rs.getInt("humidity"));
                System.out.println("Condition: " + rs.getString("condition"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching weather data: " + e.getMessage());
        }
    }
}



