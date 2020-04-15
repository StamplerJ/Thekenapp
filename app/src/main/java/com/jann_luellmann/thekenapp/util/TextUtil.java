package com.jann_luellmann.thekenapp.util;

public class TextUtil {
    public static String FirstLetterUpperCase(String text) {
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
