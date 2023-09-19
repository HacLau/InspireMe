package liushunlin.testbird.inspireme.ui

import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivityMainBinding
import liushunlin.testbird.inspireme.model.MainVM
import liushunlin.testbird.inspireme.util.viewToBitmap
import java.io.File

class MainActivity(
    override val layoutId: Int = R.layout.activity_main,
    override val TAG: String = "MainActivity"
) :
    BaseActivity<ActivityMainBinding, MainVM>() {

    override fun initData() {

        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this).get(MainVM::class.java)
        binding.mainSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        binding.mainShared.setOnClickListener {
            //let view translate bitmap to shared
            val bitmapPath = viewToBitmap(this, binding.mainCardView)
            val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", File(bitmapPath))

            startActivity(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/jpg"
            }, "shared  file to:"))
        }
    }
}