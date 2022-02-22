package com.MakeMyTrip;

import java.util.regex.Pattern;

public class EmailValidate {

    public static boolean checkEmail(String email){
        String regex = "^(.+)@(\\S+)$";
        return Pattern.compile(regex)
                .matcher(email)
                .matches();
    }
}
