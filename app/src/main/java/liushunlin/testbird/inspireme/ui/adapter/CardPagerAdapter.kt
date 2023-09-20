package liushunlin.testbird.inspireme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import liushunlin.testbird.inspireme.R
import liushunlin.testbird.inspireme.databinding.ItemMainPagerBinding
import liushunlin.testbird.inspireme.model.InspireEntity
import liushunlin.testbird.inspireme.util.getBitmapFromAssets
import liushunlin.testbird.inspireme.util.getDayOfWeek
import liushunlin.testbird.inspireme.util.getHistoryDay
import liushunlin.testbird.inspireme.util.logE
import liushunlin.testbird.inspireme.util.viewToBitmap
import java.io.File

class CardPagerAdapter(
    private val context: Context,
    private var data: List<InspireEntity>,
    private val onclick: (String) -> Unit = {}
) : PagerAdapter() {
    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ItemMainPagerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main_pager, container, false)
        binding.mainShared.setOnClickListener {
            val bitmapPath = viewToBitmap(context, binding.mainCardView,data[position].currentTime)
            bitmapPath.logE()
            onclick.invoke(bitmapPath)
            //let view translate bitmap to shared

        }
        binding.mainContent.text = data[position].content
        binding.mainDayWeek.text = getDayOfWeek(data[position].currentTime)
        binding.mainDayMonth.text = getHistoryDay(data[position].currentTime)
        binding.mainThemeImage.setImageBitmap(getBitmapFromAssets(context,"images${File.separator}${data[position].imagePath}"))
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}