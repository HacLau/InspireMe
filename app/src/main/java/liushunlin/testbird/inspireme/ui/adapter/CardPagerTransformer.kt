package liushunlin.testbird.inspireme.ui.adapter

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class CardPagerTransformer : ViewPager.PageTransformer {
    private val MIN_SCALE: Float = 0.78f
    override fun transformPage(page: View, position: Float) {
        val scale = if (position < -1) {
            MIN_SCALE
        } else if (position <= 1) {
            MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))
        }else{
            MIN_SCALE
        }
//        page.scaleX = scale
        page.scaleY = scale
    }
}