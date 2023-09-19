package liushunlin.testbird.inspireme.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivitySettingBinding
import liushunlin.testbird.inspireme.model.SettingVM

class SettingActivity(
    override val layoutId: Int = R.layout.activity_setting,
    override val TAG: String = "SettingActivity"
) :
    BaseActivity<ActivitySettingBinding, SettingVM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    override fun initData() {

        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this).get(SettingVM::class.java)
    }
}