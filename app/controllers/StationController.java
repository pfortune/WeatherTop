package controllers;
import java.util.List;

import models.Reading;
import models.Station;

import models.User;
import play.mvc.Controller;

public class StationController extends Controller {

    public static void index(){
        redirect("/login");
    }
    public static void viewStation(Long id) {
        if(session.get("logged_in_userid") == null) {
            redirect("/login");
        }

        Station station = Station.findById(id);
        if (station != null && station.user != null) {
            String userID = session.get("logged_in_userid");
            Long user = Long.parseLong(userID);
            if(!station.user.id.equals(user)) {
                flash("error", "You do not have permission to view this station");
                redirect("/dashboard");
            }
            render("station.html", station);
        } else {
            flash("error", "You do not have permission to view this station");
            redirect("/dashboard");
        }
    }



    public static void addReading(Long id, int code, double temperature, double windSpeed, int pressure, int windDirection) {
        Station station = Station.findById(id);
        if (station != null) {
            String userID = session.get("logged_in_userid");
            Long user = Long.parseLong(userID);
            if(!station.user.id.equals(user)) {
                flash("error", "You do not have permission to access this station");
                redirect("/dashboard");
            }

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
