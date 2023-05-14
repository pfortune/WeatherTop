package controllers;

import java.util.List;

import models.User;

import play.mvc.Controller;
public class AuthController extends Controller {
    public static void register() {
        render("register.html");
    }

    public static void login() {
        render("login.html");
    }

    //register a new user
    public static void registerUser(String firstname, String lastname, String email, String password) {
        if (firstname == null || firstname.trim().isEmpty()) {
            flash("error", "First name cannot be empty");
            redirect("/register");
        } else if (lastname == null || lastname.trim().isEmpty()) {
            flash("error", "Last name cannot be empty");
            redirect("/register");
        } else if (email == null || email.trim().isEmpty()) {
            flash("error", "Email cannot be empty");
            redirect("/register");
        } else if (password == null || password.trim().isEmpty()) {
            flash("error", "Password cannot be empty");
            redirect("/register");
        } else {
            User user = new User(firstname, lastname, email, password);
            user.save();
            flash("success", "Welcome, " + user.firstname);
            redirect("/dashboard");
        }
    }

    public static void authenticate(String email, String password) {
        User user = User.findByEmail(email);
        if (user == null) {
            flash("error", "Invalid email or password");
            redirect("/login");
        } else if (user.checkPassword(password)) {  // change this line
            session.put("logged_in_userid", user.id);
            flash("success", "Welcome back, " + user.firstname);
            redirect("/dashboard");
        } else {
            flash("error", "Invalid email or password");
            redirect("/login");
        }
    }

    public static void logout() {
        session.clear();
        flash("success", "You've been logged out");
        redirect("/");
    }
}
