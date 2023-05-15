package org.project.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {
    public static Boolean isPassword(String input){
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static Boolean isAlphabet(String input){
        Pattern pattern = Pattern.compile("^[A-Za-z\\s]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static Boolean isEmail(String input){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
    public static Boolean isPhoneNumber(String input){
        Pattern pattern = Pattern.compile("^(\\+62|62)?[\\s-]?0?8[1-9]{1}\\d{1}[\\s-]?\\d{4}[\\s-]?\\d{2,5}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static Boolean isGender(String input){
        Pattern pattern = Pattern.compile("^&[FM]$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static Boolean isDateFormat(String input){
        Pattern pattern = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
