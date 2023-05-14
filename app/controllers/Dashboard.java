package controllers;

import java.util.List;

import models.Station;

import play.mvc.Controller;

public class Dashboard extends Controller
{
    public static void index() {
        if(session.get("logged_in_userid") != null) {
            redirect("/dashboard");
        }
        List<Station> stations = Station.findAll();
        render("dashboard.html", stations);
    }

    public static void addStation(String title, double latitude, double longitude){
        if(title == null || title.trim().isEmpty()) {
            flash("error", "Station name cannot be empty");
            redirect("/dashboard");
        } else if(latitude < -90 || latitude > 90) {
            flash("error", "Invalid latitude. Please enter a value between -90 and 90.");
            redirect("/dashboard");
        } else if(longitude < -180 || longitude > 180) {
            flash("error", "Invalid longitude. Please enter a value between -180 and 180.");
            redirect("/dashboard");
        } else {
            Station station = new Station(title, latitude, longitude);
            station.save();
            flash("success", "Station added successfully");
            redirect("/dashboard");
        }
    }
}
