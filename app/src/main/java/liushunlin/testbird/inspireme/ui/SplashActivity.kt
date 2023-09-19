package liushunlin.testbird.inspireme.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivitySplashBinding
import liushunlin.testbird.inspireme.model.SplashVM
import liushunlin.testbird.inspireme.util.toast

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashVM>() {
    override val layoutId: Int
        get() = R.layout.activity_splash
    override val TAG: String
        get() = "SplashActivity"

    override fun initData() {
        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this)[SplashVM::class.java]
        binding.spStart.setOnClickListener {
            if (binding.spCheck.isChecked) {
                startActivity(Intent(this, StartActivity::class.java).apply {

                })
                finish()
            }
            else
                "Please check privacy".toast(this)
        }
    }
}