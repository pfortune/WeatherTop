package controllers;

import models.User;
import play.Logger;
import play.mvc.Controller;

public class AuthController extends Controller {
  public static void register() {
    if (getLoggedInUser() != null) {
      redirect("/dashboard");
    }
    render("register.html");
  }

  public static void login() {
    if (getLoggedInUser() != null) {
      redirect("/dashboard");
    }
    render("login.html");
  }

  public static void authenticate(String email, String password) {
    User user = User.findByEmail(email);
    if (user == null) {
      flash("error", "Invalid email or password");
      redirect("/login");
    } else if (user.checkPassword(password)) {
      Logger.info("Authentication successful");
      session.put("logged_in_userid", user.id);
      flash("success", "Welcome back, " + user.firstname);
      redirect("/dashboard");
    } else {
      flash("error", "Invalid email or password");
      redirect("/login");
    }
  }

  //register a new user
  public static void registerUser(String firstname, String lastname, String email, String password) {
    User user = new User();
    validateAndSetUserDetails(user, firstname, lastname, email, password, "/register");
    session.put("logged_in_userid", user.id);
    flash("success", "Welcome, " + user.firstname);
    redirect("/dashboard");
  }

  public static void updateAccount(String firstname, String lastname, String email, String password) {
    if (getLoggedInUser() == null) {
      redirect("/login");
    }
    User user = getLoggedInUser();
    validateAndSetUserDetails(user, firstname, lastname, email, password, "/account");
    flash("success", "Account updated successfully");
    redirect("/account");
  }

  private static void validateAndSetUserDetails(User user, String firstname, String lastname, String email, String password, String redirectUrl) {
    if (firstname == null || firstname.trim().isEmpty()) {
      flash("error", "First name cannot be empty");
      redirect(redirectUrl);
    } else if (lastname == null || lastname.trim().isEmpty()) {
      flash("error", "Last name cannot be empty");
      redirect(redirectUrl);
    } else if (email == null || email.trim().isEmpty()) {
      flash("error", "Email cannot be empty");
      redirect(redirectUrl);
    } else {
      User existingUser = User.findByEmail(email);
      if (existingUser != null && !existingUser.id.equals(user.id)) {
        flash("error", "This email is already in use. Please use a different email.");
        redirect(redirectUrl);
      }
      user.firstname = firstname;
      user.lastname = lastname;
      user.email = email;
      if (password != null && !password.trim().isEmpty()) {
        if (!user.setPassword(password)) {
          flash("error", "Password must be at least 8 characters long and include numbers, and both upper and lowercase letters");
          redirect(redirectUrl);
        }
      }
      user.save();
    }
  }

  public static void showAccount() {
    if (getLoggedInUser() == null) {
      redirect("/login");
    }
    User user = getLoggedInUser();
    render("account.html", user);
  }

  public static User getLoggedInUser() {
    String userId = session.get("logged_in_userid");
    if (userId != null) {
      return User.findById(Long.parseLong(userId));
    }
    return null;
  }

  public static void logout() {
    session.clear();
    flash("success", "You've been logged out");
    redirect("/");
  }
}
