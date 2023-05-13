package controllers;

import java.util.List;

import models.Station;

import play.mvc.Controller;

public class Dashboard extends Controller
{
    public static void index() {
        List<Station> stations = Station.findAll();
        render("dashboard.html", stations);
    }

    public static void addStation(String title, double latitude, double longitude){
        if(title == null || title.trim().isEmpty()) {
            flash("error", "Station name cannot be empty");
            redirect("/dashboard");
        } else {
            Station station = new Station(title, latitude, longitude);
            station.save();
            flash("success", "Station added successfully");
            redirect("/dashboard");
        }
    }
}
