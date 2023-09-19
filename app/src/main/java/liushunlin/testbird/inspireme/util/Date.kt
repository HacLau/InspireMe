package liushunlin.testbird.inspireme.util

import java.util.Calendar
import java.util.Date

fun getToday(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DATE)
    return "$day-$month-$year"
}

fun getHistoryDay(millis:Long):String{
    val date = Date(millis)
    val year = date.year
    val month = date.month + 1
    val day = date.day
    return "$day-$month-$year"
}