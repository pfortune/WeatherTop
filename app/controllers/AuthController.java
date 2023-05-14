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
}
