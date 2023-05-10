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
}
