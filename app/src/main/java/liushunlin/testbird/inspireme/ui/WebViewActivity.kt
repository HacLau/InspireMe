package liushunlin.testbird.inspireme.ui

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivityWebviewBinding
import liushunlin.testbird.inspireme.viewmodel.WebViewVM

class WebViewActivity(
    override val layoutId: Int = R.layout.activity_webview,
    override val TAG: String = "WebViewActivity"
) :
    BaseActivity<WebViewVM>() {

    private lateinit var binding: ActivityWebviewBinding
    override fun onCreate() {
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {

        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this)[WebViewVM::class.java]
        intent.getStringExtra("url")?.let {
            binding.mainWebview.loadUrl(it)
        }
    }
}