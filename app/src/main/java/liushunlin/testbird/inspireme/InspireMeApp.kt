package liushunlin.testbird.inspireme

import android.app.Application
import com.tencent.mmkv.MMKV

class InspireMeApp:Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}