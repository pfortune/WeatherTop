package controllers;

import java.util.List;

import models.Station;
import models.Reading;

import utils.Conversion;

import play.Logger;
import play.mvc.Controller;

public class Dashboard extends Controller
{
    public static void index()
    {
        List<Station> stations = Station.findAll();
        render("dashboard.html", stations);
    }
}
