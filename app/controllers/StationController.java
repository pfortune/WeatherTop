package controllers;

import models.Reading;
import models.Station;

import models.User;
import play.Logger;
import play.mvc.Controller;

public class StationController extends Controller {

    public static void index(){
        redirect("/login");
    }
    public static void viewStation(Long id) {
        User user = AuthController.getLoggedInUser();
        if(user != null) {
            Station station = Station.findById(id);
            boolean isUserStation = user.stations.contains(station);

            if (isUserStation) {
                render("station.html", station);
            } else {
                flash("error", "You do not have permission to view this station");
                redirect("/dashboard");
            }
        } else {
            redirect("/login");
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

    public static void deleteReading(Long id, Long readingid) {
        Logger.info("Deleting " + readingid);
        Station station = Station.findById(id);
        Reading reading = Reading.findById(readingid);
        if(station != null) {
            if(reading != null) {
                station.readings.remove(reading);
                station.save();
                reading.delete();
                flash("success", "Reading deleted successfully");
            } else {
                flash("error", "Reading not found");
            }
        } else {
            flash("error", "Station not found");
        }
        redirect("/station/" + id);
    }

}
