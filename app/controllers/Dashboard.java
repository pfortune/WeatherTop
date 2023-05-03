package controllers;

import java.util.List;

import models.Station;
import models.Reading;

import utils.Conversion;

import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller
{
    public static void index() {
        List<Station> stations = Station.findAll();

        for (Station station : stations) {
            if (!station.readings.isEmpty()) {
                Reading latestReading = station.getLatestReading();

                String weatherDescription = Conversion.weatherCodeToCondition(latestReading.code);
                double temperatureFahrenheit = Conversion.celsiusToFahrenheit(latestReading.temperature);
                int windBeaufort = Conversion.kmhToBeaufort(latestReading.windSpeed);
                String beaufortDescription = Conversion.beaufortDescription(windBeaufort);

                station.latestWeatherDescription = weatherDescription;
                station.latestTemperatureFahrenheit = temperatureFahrenheit;
                station.latestWindBeaufort = beaufortDescription;
            }
        }

        render("dashboard.html", stations);
    }
}
