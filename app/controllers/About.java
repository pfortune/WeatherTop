package controllers;

import play.mvc.Controller;

/**
 * Controller for the About page.
 */
public class About extends Controller {

  /**
   * Display the About page.
   */
  public static void index() {
    render("about.html");
  }
}
