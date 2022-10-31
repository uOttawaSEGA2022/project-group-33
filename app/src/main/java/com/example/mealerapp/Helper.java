package com.example.mealerapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private static final String regex = "^(.+)@(.+)$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

}
