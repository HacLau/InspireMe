package liushunlin.testbird.inspireme.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.blankj.utilcode.util.TimeUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import liushunlin.testbird.inspireme.model.InspireEntity

private const val ONE_DAY_MILLIS = 86400
fun getJsonFromAssets(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    val size = stream.available()
    val buffer = ByteArray(size)
    stream.read(buffer)
    stream.close()
    return String(buffer, Charsets.UTF_8)
}

fun getBitmapFromAssets(context: Context, fileName: String): Bitmap? {
    val stream = context.assets.open(fileName)
    val bitmap = BitmapFactory.decodeStream(stream)
    stream.close()
    return bitmap
}

fun getInspireData(context: Context): MutableList<InspireEntity> {
    val currentMillis = System.currentTimeMillis()
    val currentDay = getMillisDay(currentMillis)
    val historyDay = getMillisDay(MMKVHelper.isFirstLaunchTime - 2 * ONE_DAY_MILLIS * 1000)
    val passDay = if (currentDay > historyDay && TimeUtils.isLeapYear(MMKVHelper.isFirstLaunchTime)) {
        currentDay + 366 - historyDay
    } else {
        currentDay - historyDay
    }
    val mutableList: MutableList<InspireEntity> = mutableListOf()
    val list: List<String> = Gson().fromJson(getJsonFromAssets(context, "inspire.json"), object : TypeToken<List<String>>() {}.type)
    for (index in 0 .. if (passDay > list.size) list.size - 1 else passDay) {
        val time = if (index <= passDay % list.size) {
            currentMillis + ONE_DAY_MILLIS * 1000L * (index - passDay % list.size)
        } else {
            currentMillis + ONE_DAY_MILLIS * 1000L * (-index)
        }
        mutableList.add(
            InspireEntity(
                id = index,
                content = list[index],
                currentTime = time,
                imagePath = "images${index % 16}.png"
            )
        )
    }

    return mutableList.apply {
        sortBy {
            it.currentTime
        }
    }

}


