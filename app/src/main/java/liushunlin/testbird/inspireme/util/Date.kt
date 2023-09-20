package liushunlin.testbird.inspireme.util

import android.annotation.SuppressLint
import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

@SuppressLint("SimpleDateFormat")
fun getHistoryDay(millis: Long): String {
    return TimeUtils.millis2String(millis, SimpleDateFormat("MM/dd yyyy"))
}

fun getDayOfWeek(millis: Long): String {
    return when(TimeUtils.getValueByCalendarField(millis,Calendar.DAY_OF_WEEK)){
        1-> "Sunday"
        2-> "Monday"
        3-> "Tuesday"
        4-> "Wednesday"
        5-> "Thursday"
        6-> "Friday"
        7-> "Saturday"
        else -> {""}
    }
}

fun getMillisDay(millis: Long):Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millis
    return calendar.get(Calendar.DAY_OF_YEAR)
}
