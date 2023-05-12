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

    public static void addStation(String title){
        Station station = new Station(title);
        station.save();
        redirect("/dashboard");
    }
}
