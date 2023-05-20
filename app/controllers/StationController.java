package controllers;

import models.Reading;
import models.Station;
import models.User;
import play.Logger;
import play.mvc.Controller;

/**
 * The controller for actions related to the Station model.
 * This includes actions for viewing a station, adding a reading to a station, and deleting a reading from a station.
 */
public class StationController extends Controller {

  /**
   * Redirects to the login page.
   */
  public static void index() {
    redirect("/login");
  }

  /**
   * Renders a station view if the logged in user has permission to view the station.
   * @param id The id of the station to view.
   */
  public static void viewStation(Long id) {
    User user = AuthController.getLoggedInUser();
    if (user != null) {
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

  /**
   * Adds a reading to a station.
   * @param id The id of the station to add a reading to.
   * @param code The weather code of the reading.
   * @param temperature The temperature of the reading.
   * @param windSpeed The wind speed of the reading.
   * @param pressure The pressure of the reading.
   * @param windDirection The wind direction of the reading.
   */
  public static void addReading(Long id, int code, double temperature, double windSpeed, int pressure, int windDirection) {
    Station station = Station.findById(id);
    if (station != null) {
      if (code == 0 || temperature == 0.0 || windSpeed == 0.0 || pressure == 0 || windDirection == 0) {
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

  /**
   * Deletes a reading from a station.
   * @param id The id of the station to delete a reading from.
   * @param readingid The id of the reading to delete.
   */
  public static void deleteReading(Long id, Long readingid) {
    Logger.info("Deleting " + readingid);
    Station station = Station.findById(id);
    Reading reading = Reading.findById(readingid);
    if (station != null) {
      if (reading != null) {
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
