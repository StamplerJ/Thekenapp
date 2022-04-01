package com.jann_luellmann.thekenapp.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TextUtil {
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY)
    fun dateToString(date: Date?): String {
        return dateFormat.format(date)
    }

    fun stringToDate(date: String?): Date? {
        return try {
            dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun FirstLetterUpperCase(text: String): String {
        if (text.isEmpty()) return text
        val parts = text.split(" ").toTypedArray()
        return if (parts.size == 1) text.substring(0, 1).toUpperCase() + text.substring(1)
            .toLowerCase() else {
            val result = StringBuilder()
            for (part in parts) if (part.length >= 2) result.append(
                part.substring(0, 1).toUpperCase()
            ).append(part.substring(1).toLowerCase())
                .append(" ") else result.append(part.toUpperCase())
            result.toString().trim { it <= ' ' }
        }
    }
}