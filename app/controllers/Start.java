package controllers;

import models.User;
import play.mvc.Controller;

public class Start extends Controller {
  public static void index() {
    User user = AuthController.getLoggedInUser();
    if (user != null) {
      redirect("/dashboard");
    }

    render("start.html");
  }
}
