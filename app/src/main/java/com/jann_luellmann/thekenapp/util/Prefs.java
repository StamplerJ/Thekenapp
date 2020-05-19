package com.jann_luellmann.thekenapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs
{
    public static final String CURRENT_EVENT = "CURRENT_EVENT";

    private static SharedPreferences getPreferences(Context context)
    {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void putString(Context context, String key, String value)
    {
        getPreferences(context).edit().putString(key, value).apply();
    }

    public static void putInt(Context context, String key, int value)
    {
        getPreferences(context).edit().putInt(key, value).apply();
    }

    public static void putLong(Context context, String key, long value)
    {
        getPreferences(context).edit().putLong(key, value).apply();
    }

    public static boolean containsKey(Context context, String key)
    {
        try
        {
            return getPreferences(context).contains(key);
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static String getString(Context context, String key)
    {
        return getPreferences(context).getString(key, null);
    }

    public static String getString(Context context, String key, String defaultValue)
    {
        return getPreferences(context).getString(key, defaultValue);
    }

    public static Boolean getBoolean(Context context, String key, Boolean defaultValue)
    {
        return getPreferences(context).getBoolean(key, defaultValue);
    }

    public static int getInt(Context context, String key, int defaultValue)
    {
        return getPreferences(context).getInt(key, defaultValue);
    }

    public static long getLong(Context context, String key, long defaultValue)
    {
        return getPreferences(context).getLong(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, Boolean value)
    {
        getPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static float getDouble(Context context, String key, float defaultValue)
    {
        return getPreferences(context).getFloat(key, defaultValue);
    }

    public static void putDouble(Context context, String key, float value)
    {
        getPreferences(context).edit().putFloat(key, value).apply();
    }

    public static void deleteKey(Context context, String key)
    {
        getPreferences(context).edit().remove(key).apply();
    }
}