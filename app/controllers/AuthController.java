package controllers;

import models.User;
import play.Logger;
import play.mvc.Controller;

/**
 * The controller for authentication.
 * It provides functionality such as registration, login, logout, and account update.
 */
public class AuthController extends Controller {
  /**
   * Display the registration page.
   */
  public static void register() {
    if (getLoggedInUser() != null) {
      redirect("/dashboard");
    }
    render("register.html");
  }

  /**
   * Display the login page.
   */
  public static void login() {
    if (getLoggedInUser() != null) {
      redirect("/dashboard");
    }
    render("login.html");
  }

  /**
   * Authenticate a user with provided email and password.
   *
   * @param email    the email of the user
   * @param password the password of the user
   */
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

  /**
   * Register a new user with provided details.
   *
   * @param firstname the first name of the user
   * @param lastname  the last name of the user
   * @param email     the email of the user
   * @param password  the password of the user
   */
  public static void registerUser(String firstname, String lastname, String email, String password) {
    User user = new User();
    validateAndSetUserDetails(user, firstname, lastname, email, password, "/register");
    session.put("logged_in_userid", user.id);
    flash("success", "Welcome, " + user.firstname);
    redirect("/dashboard");
  }

  /**
   * Update the logged in user's account with provided details.
   *
   * @param firstname the new first name of the user
   * @param lastname  the new last name of the user
   * @param email     the new email of the user
   * @param password  the new password of the user
   */
  public static void updateAccount(String firstname, String lastname, String email, String password) {
    if (getLoggedInUser() == null) {
      redirect("/login");
    }
    User user = getLoggedInUser();
    validateAndSetUserDetails(user, firstname, lastname, email, password, "/account");
    flash("success", "Account updated successfully");
    redirect("/account");
  }

  /**
   * Validate user details and save to the provided User object.
   *
   * @param user        the User object to save the details to
   * @param firstname   the first name of the user
   * @param lastname    the last name of the user
   * @param email       the email of the user
   * @param password    the password of the user
   * @param redirectUrl the URL to redirect to in case of validation failure
   */
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

  /**
   * Display the logged in user's account page.
   */
  public static void showAccount() {
    if (getLoggedInUser() == null) {
      redirect("/login");
    }
    User user = getLoggedInUser();
    render("account.html", user);
  }

  /**
   * Get the currently logged in user.
   *
   * @return the currently logged in user
   */
  public static User getLoggedInUser() {
    String userId = session.get("logged_in_userid");
    if (userId != null) {
      return User.findById(Long.parseLong(userId));
    }
    return null;
  }

  /**
   * Log out the currently logged in user.
   */
  public static void logout() {
    session.clear();
    flash("success", "You've been logged out");
    redirect("/");
  }
}
