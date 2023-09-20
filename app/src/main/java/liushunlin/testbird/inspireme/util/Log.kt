package liushunlin.testbird.inspireme.util

import android.util.Log
import liushunlin.testbird.inspireme.BuildConfig

const val TAG = "InspireMe"
fun String?.logE() {
    if (BuildConfig.DEBUG)
        Log.e(TAG, this ?: "")
}