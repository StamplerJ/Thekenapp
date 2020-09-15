package com.jann_luellmann.thekenapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TextUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String FirstLetterUpperCase(String text) {
        if(text.isEmpty())
            return text;

        String[] parts = text.split(" ");
        if(parts.length == 1)
            return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
        else {
            StringBuilder result = new StringBuilder();
            for (String part : parts)
                if(part.length() >= 2)
                    result.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase()).append(" ");
                else
                    result.append(part.toUpperCase());

            return result.toString().trim();
        }
    }
}
