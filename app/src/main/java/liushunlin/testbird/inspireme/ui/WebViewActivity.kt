package liushunlin.testbird.inspireme.ui

import androidx.lifecycle.ViewModelProvider
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.base.BaseActivity
import liushunlin.testbird.inspireme.databinding.ActivityWebviewBinding
import liushunlin.testbird.inspireme.viewmodel.WebViewVM

class WebViewActivity(
    override val layoutId: Int = R.layout.activity_webview,
    override val TAG: String = "WebViewActivity"
) :
    BaseActivity<ActivityWebviewBinding, WebViewVM>() {

    override fun initData() {

        setStatusBarTransparent(this)
        setStatusBarLightMode(this, true)
        viewModel = ViewModelProvider(this)[WebViewVM::class.java]
        intent.getStringExtra("url")?.let {
            binding.mainWebview.loadUrl(it)
        }
    }
}