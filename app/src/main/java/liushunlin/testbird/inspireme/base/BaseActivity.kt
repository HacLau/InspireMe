package liushunlin.testbird.inspireme.base

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import liushunlin.testbird.inspireme.ui.WebViewActivity

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
    protected lateinit var viewModel: VM
    abstract val layoutId: Int
    abstract val TAG: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate()
        initData()
    }

    abstract fun onCreate()

    abstract fun initData()

    open fun setStatusBarLightMode(activity: Activity, isLightMode: Boolean) {
        val window = activity.window
        var option = window.decorView.systemUiVisibility
        option = if (isLightMode) {
            option or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            option and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        window.decorView.systemUiVisibility = option
    }
    open fun setStatusBarTransparent(activity: Activity) {
        val window = activity.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val option =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = option
        window.statusBarColor = Color.TRANSPARENT
    }

    open fun startWebViewActivity(url:String){
        startActivity(Intent(this, WebViewActivity::class.java).apply {
            putExtra("url",url)
        })
    }
}