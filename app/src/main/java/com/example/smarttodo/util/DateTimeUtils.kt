package com.example.smarttodo.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    private val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())

    fun formatToShortDate(timestamp: Long): String {
        return dateFormatter.format(Date(timestamp))
    }

    fun formatToTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        return timeFormatter.format(calendar.time)
    }

    fun formatToFullSchedule(dateTimestamp: Long, hour: Int, minute: Int): String {
        val datePart = formatToShortDate(dateTimestamp)
        val timePart = formatToTime(hour, minute)
        return "$datePart, $timePart"
    }

    fun getRelativeLabel(fullSchedule: String?): String {
        if (fullSchedule == null) return ""
        return try {
            val parts = fullSchedule.split(", ")
            if (parts.size >= 2) {
                val datePart = parts[0] + ", " + parts[1] // "MMM dd, yyyy"
                val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = sdf.parse(datePart) ?: return fullSchedule
                
                val calendar = Calendar.getInstance()
                calendar.time = date
                
                val now = Calendar.getInstance()
                
                val calendarDay = calendar.get(Calendar.DAY_OF_YEAR)
                val calendarYear = calendar.get(Calendar.YEAR)
                
                val nowDay = now.get(Calendar.DAY_OF_YEAR)
                val nowYear = now.get(Calendar.YEAR)
                
                val relativeDate = when {
                    calendarYear == nowYear && calendarDay == nowDay -> "Today"
                    calendarYear == nowYear && calendarDay == nowDay + 1 -> "Tomorrow"
                    else -> parts[0] + ", " + parts[1]
                }
                
                if (parts.size > 2) "$relativeDate, ${parts[2]}" else relativeDate
            } else fullSchedule
        } catch (e: Exception) {
            fullSchedule
        }
    }
}
