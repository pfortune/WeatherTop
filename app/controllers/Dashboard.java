package controllers;

import java.util.List;

import models.Station;
import models.User;
import play.mvc.Controller;

public class Dashboard extends Controller {
  public static void index() {
    User user = AuthController.getLoggedInUser();
    if (user == null) {
      redirect("/login");
    }

    List<Station> stations = user.stations;
    render("dashboard.html", stations);
  }

  public static void addStation(String title, double latitude, double longitude) {
    if (title == null || title.trim().isEmpty()) {
      flash("error", "Station name cannot be empty");
      redirect("/dashboard");
    } else if (latitude < -90 || latitude > 90) {
      flash("error", "Invalid latitude. Please enter a value between -90 and 90.");
      redirect("/dashboard");
    } else if (longitude < -180 || longitude > 180) {
      flash("error", "Invalid longitude. Please enter a value between -180 and 180.");
      redirect("/dashboard");
    } else {
      User user = AuthController.getLoggedInUser();
      if (user == null) {
        flash("error", "You do not have permission to add a station");
        redirect("/dashboard");
      } else {
        Station station = new Station(title, latitude, longitude);
        user.stations.add(station);
        user.save();
        flash("success", "Station added successfully");
        redirect("/dashboard");
      }
    }
  }

  public static void deleteStation(Long id) {
    User user = AuthController.getLoggedInUser();
    if (user == null) {
      flash("error", "You do not have permission to delete a station");
      redirect("/dashboard");
    } else {
      Station station = Station.findById(id);
      if (station == null) {
        flash("error", "Station not found");
        redirect("/dashboard");
      } else {
        user.stations.remove(station);
        user.save();
        station.delete();
        flash("success", "Station deleted successfully");
        redirect("/dashboard");
      }
    }
  }

}
