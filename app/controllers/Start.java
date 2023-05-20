package controllers;

import models.User;
import play.mvc.Controller;

/**
 * The controller for the starting page of the application.
 * It checks if the user is already logged in, and if so, redirects to the dashboard.
 * If not, it renders the start page.
 */
public class Start extends Controller {

  /**
   * Checks if a user is already logged in. If so, redirects to the dashboard.
   * If not, renders the start page.
   */
  public static void index() {
    User user = AuthController.getLoggedInUser();
    if (user != null) {
      redirect("/dashboard");
    }

    render("start.html");
  }
}
