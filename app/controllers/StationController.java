package controllers;
import java.util.List;

import models.Reading;
import models.Station;

import play.mvc.Controller;

public class StationController extends Controller {
    public static void index(Long id) {
        if(session.get("logged_in_userid") == null) {
            redirect("/");
        }

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
            if(code == 0) {
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
