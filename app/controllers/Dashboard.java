package controllers;

import java.util.Comparator;
import java.util.List;

import models.Station;
import models.User;
import play.mvc.Controller;

/**
 * The controller for the user's dashboard.
 * It provides functionality such as listing stations, adding a station and deleting a station.
 */
public class Dashboard extends Controller {
  /**
   * Display stations sorted alphabetically. Pagination is also applied.
   *
   * @param page the page number to display, null defaults to the first page.
   */
  public static void index(Integer page) {
    User user = AuthController.getLoggedInUser();
    if (user == null) {
      redirect("/login");
    }

    if (page == null) {
      page = 1;
    }

    List<Station> allStations = sortStations(user.stations); // sort stations alphabetically

    int pageSize = 3;  // or whatever number you choose
    int pages = (allStations.size() + pageSize - 1) / pageSize;
    int start = (page - 1) * pageSize;
    int end = Math.min(start + pageSize, allStations.size());

    List<Station> stations = allStations.subList(start, end);
    render("dashboard.html", stations, page, pages);
  }

  /**
   * Sort the stations list alphabetically
   *
   * @param stations list of stations to be sorted
   * @return the sorted list of stations
   */
  public static List<Station> sortStations(List<Station> stations) {
    stations.sort(Comparator.comparing(Station::getName, String.CASE_INSENSITIVE_ORDER));
    return stations;
  }

  /**
   * Add a new station
   *
   * @param title     the name of the station
   * @param latitude  the latitude of the station
   * @param longitude the longitude of the station
   */
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
        redirect("/login");
      } else {
        Station station = new Station(title, latitude, longitude);
        user.stations.add(station);
        user.save();
        flash("success", "Station added successfully");
        redirect("/dashboard");
      }
    }
  }

  /**
   * Delete a station
   *
   * @param id the id of the station to be deleted
   */
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
