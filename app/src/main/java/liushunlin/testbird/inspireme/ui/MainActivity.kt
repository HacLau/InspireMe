package liushunlin.testbird.inspireme.ui

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.util.TypedValue
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import liushunlin.testbird.inspireme.BuildConfig
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivityMainBinding
import liushunlin.testbird.inspireme.model.InspireEntity
import liushunlin.testbird.inspireme.ui.adapter.CardPagerAdapter
import liushunlin.testbird.inspireme.ui.adapter.CardPagerTransformer
import liushunlin.testbird.inspireme.util.URL_PRIVACY
import liushunlin.testbird.inspireme.util.URL_USER_AGREEMENT
import liushunlin.testbird.inspireme.util.blurBitmap
import liushunlin.testbird.inspireme.util.getBitmapFromAssets
import liushunlin.testbird.inspireme.util.getInspireData
import liushunlin.testbird.inspireme.util.logE
import liushunlin.testbird.inspireme.util.viewToBitmap
import liushunlin.testbird.inspireme.viewmodel.MainVM
import java.io.File


class MainActivity(
    override val layoutId: Int = R.layout.activity_main,
    override val TAG: String = "MainActivity"
) :
    BaseActivity<ActivityMainBinding, MainVM>() {
    var dataList: MutableList<InspireEntity>? = null
    override fun initData() {
        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this)[MainVM::class.java]

        binding.drawerLogo.setOnClickListener {

        }
        binding.mainSetting.setOnClickListener {
            binding.mainDrawer.openDrawer(GravityCompat.START)
        }

        binding.menuAgreement.setOnClickListener {
            startWebViewActivity(URL_USER_AGREEMENT)
        }
        binding.menuPrivacy.setOnClickListener {
            startWebViewActivity(URL_PRIVACY)
        }

        binding.menuShare.setOnClickListener {
            kotlin.runCatching {
                startActivity(Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                })
            }
            binding.mainDrawer.closeDrawer(GravityCompat.START)
        }


        binding.mainPager.offscreenPageLimit = 3
        dataList = getInspireData(this)
        binding.mainPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                setMainLayoutBackground(position)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        binding.mainPager.adapter = CardPagerAdapter(this, dataList!!) {
            val bitmapPath = viewToBitmap(this, binding.mainLayout,it)
            bitmapPath.logE()
            val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", File(bitmapPath))
            startActivity(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/jpg"
            }, "shared  file to:"))
        }
        binding.mainPager.currentItem = dataList!!.size - 1
        if (dataList!!.size == 1) {
            setMainLayoutBackground(0)
        }
        binding.mainPager.setPageTransformer(false, CardPagerTransformer())

        binding.mainPager.pageMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            12f, resources.displayMetrics
        ).toInt()
    }

    private fun setMainLayoutBackground(position: Int) {
        val bitmap = getBitmapFromAssets(this@MainActivity, "images${File.separator}${dataList!![position].imagePath}")?.blurBitmap(this@MainActivity)
        binding.mainLayout.background = BitmapDrawable(resources, bitmap)
    }

}