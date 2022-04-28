package com.jann_luellmann.thekenapp.util

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    const val CURRENT_EVENT = "CURRENT_EVENT"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    fun putCurrentEvent(context: Context, eventId: Long) {
        getPreferences(context).edit().putLong(CURRENT_EVENT, eventId).apply()
    }

    fun getCurrentEvent(context: Context): Long {
        return getPreferences(context).getLong(CURRENT_EVENT, -1)
    }

    /** ---------------------------------------------------------------------- **/

//    fun putString(context: Context, key: String?, value: String?) {
//        getPreferences(context).edit().putString(key, value).apply()
//    }
//
//    fun putInt(context: Context, key: String?, value: Int) {
//        getPreferences(context).edit().putInt(key, value).apply()
//    }
//
//    fun putLong(context: Context, key: String?, value: Long) {
//        getPreferences(context).edit().putLong(key, value).apply()
//    }
//
//    fun containsKey(context: Context, key: String?): Boolean {
//        return try {
//            getPreferences(context).contains(key)
//        } catch (e: Exception) {
//            false
//        }
//    }
//
//    fun getString(context: Context, key: String?): String? {
//        return getPreferences(context).getString(key, null)
//    }
//
//    fun getString(context: Context, key: String?, defaultValue: String?): String? {
//        return getPreferences(context).getString(key, defaultValue)
//    }
//
//    fun getBoolean(context: Context, key: String?, defaultValue: Boolean?): Boolean {
//        return getPreferences(context).getBoolean(key, defaultValue!!)
//    }
//
//    fun getInt(context: Context, key: String?, defaultValue: Int): Int {
//        return getPreferences(context).getInt(key, defaultValue)
//    }
//
//    fun getLong(context: Context, key: String?, defaultValue: Long): Long {
//        return getPreferences(context).getLong(key, defaultValue)
//    }
//
//    fun putBoolean(context: Context, key: String?, value: Boolean?) {
//        getPreferences(context).edit().putBoolean(key, value!!).apply()
//    }
//
//    fun getDouble(context: Context, key: String?, defaultValue: Float): Float {
//        return getPreferences(context).getFloat(key, defaultValue)
//    }
//
//    fun putDouble(context: Context, key: String?, value: Float) {
//        getPreferences(context).edit().putFloat(key, value).apply()
//    }
//
//    fun deleteKey(context: Context, key: String?) {
//        getPreferences(context).edit().remove(key).apply()
//    }
}