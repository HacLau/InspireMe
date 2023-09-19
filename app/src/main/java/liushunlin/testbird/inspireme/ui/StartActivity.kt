package liushunlin.testbird.inspireme.ui

import android.content.Intent
import android.os.CountDownTimer
import androidx.lifecycle.ViewModelProvider
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivityStartBinding
import liushunlin.testbird.inspireme.model.StartVM
import java.util.concurrent.TimeUnit
private const val COUNTER_TIME_MILLIS = 5000L
class StartActivity(
    override val layoutId: Int = R.layout.activity_start,
    override val TAG: String = "StartActivity"
) :
    BaseActivity<ActivityStartBinding, StartVM>() {
    private var millisRemaining :Long= 0L
    override fun initData() {
        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this).get(StartVM::class.java)
        startTimer(COUNTER_TIME_MILLIS)
    }

    private fun startTimer(time:Long){
        val timer = object : CountDownTimer(time, 33) {
            override fun onTick(millisUntilFinished: Long) {
                millisRemaining = TimeUnit.MILLISECONDS.toMillis(millisUntilFinished) + 1
                binding.stPb.progress = 100 - (millisRemaining / 50).toInt()
            }

            override fun onFinish() {
                millisRemaining = 0
                binding.stPb.progress = 100
                startMainActivity()
            }

        }
        timer.start()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}