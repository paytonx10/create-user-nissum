package com.nissum.createuser.utils;

import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if(ObjectUtils.isEmpty(email)){
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}