package liushunlin.testbird.inspireme.ui

import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.lifecycle.ViewModelProvider
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivitySplashBinding
import liushunlin.testbird.inspireme.util.RepositoryUtils
import liushunlin.testbird.inspireme.util.logE
import liushunlin.testbird.inspireme.util.toast
import liushunlin.testbird.inspireme.viewmodel.SplashVM
import java.util.concurrent.TimeUnit

private const val COUNTER_TIME_MILLIS = 5000L

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashVM>() {
    override val layoutId: Int
        get() = R.layout.activity_splash
    override val TAG: String
        get() = "SplashActivity"
    private var millisRemaining: Long = 0L
    override fun initData() {
        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this)[SplashVM::class.java]
        binding.spStart.setOnClickListener {
            if (binding.spCheck.isChecked) {
                binding.spCheck.visibility = View.GONE
                binding.spStart.visibility = View.GONE
                binding.spPrivacy.visibility = View.GONE
                binding.spProgress.visibility = View.VISIBLE
                startTimer(COUNTER_TIME_MILLIS)

            } else
                "Please check privacy".toast(this)
        }
        "$RepositoryUtils.isFirstLaunch $RepositoryUtils.isFirstLaunchTime".logE()
        if (RepositoryUtils.isFirstLaunch) {
            RepositoryUtils.isFirstLaunch = false
            RepositoryUtils.isFirstLaunchTime = System.currentTimeMillis()
        }
        val spannableString = SpannableString("I have read and agreed to the \"Privacy Policy\" and \"User Agreement\"")
        spannableString.setSpan(object :ClickableSpan(){
            override fun onClick(widget: View) {
                startWebViewActivity("https://www.jianshu.com/p/58c98ceefe7c")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#FFFFFF")
            }
        },30,46,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object :ClickableSpan(){
            override fun onClick(widget: View) {
                startWebViewActivity("https://www.python100.com/html/111593.html")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#FFFFFF")
            }
        },51,67,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.spPrivacy.text = spannableString
        binding.spPrivacy.highlightColor = Color.parseColor("#00000000")
        binding.spPrivacy.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun startTimer(time: Long) {
        val timer = object : CountDownTimer(time, 33) {
            override fun onTick(millisUntilFinished: Long) {
                millisRemaining = TimeUnit.MILLISECONDS.toMillis(millisUntilFinished) + 1
                binding.spProgress.progress = 100 - (millisRemaining / 50).toInt()
            }

            override fun onFinish() {
                millisRemaining = 0
                binding.spProgress.progress = 100
                startMainActivity()
            }

        }
        timer.start()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java).apply {

        })
        finish()
    }
}