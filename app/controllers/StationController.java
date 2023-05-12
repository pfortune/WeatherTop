package controllers;

import models.Reading;
import models.Station;

import play.mvc.Controller;

public class StationController extends Controller {
    public static void index(Long id) {
        Station station = Station.findById(id);
        if (station != null) {
            render("station.html", station);
        } else {
            flash("error", "Station not found");
            redirect("/dashboard");
        }
    }


    public static void addReading(Long id, int code, double temperature, double windSpeed, int pressure, int windDirection) {
        Station station = Station.findById(id);
        if (station != null) {
            if(code == 0 || temperature == 0.0 || windSpeed == 0.0 || pressure == 0 || windDirection == 0) {
                flash("error", "Reading fields cannot be empty");
            } else {
                Reading reading = new Reading(code, temperature, windSpeed, pressure, windDirection);
                station.readings.add(reading);
                station.save();
                flash("success", "Reading added successfully");
            }
        } else {
            flash("error", "Station not found");
        }
        redirect("/station/" + id);
    }

}
