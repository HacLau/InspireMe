package liushunlin.testbird.inspireme.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import liushunlin.testbird.inspireme.databinding.ItemMainPagerBinding
import liushunlin.testbird.inspireme.model.InspireEntity
import liushunlin.testbird.inspireme.util.getBitmapFromAssets
import liushunlin.testbird.inspireme.util.getDayOfWeek
import liushunlin.testbird.inspireme.util.getHistoryDay
import java.io.File

class CardPagerAdapter(
    private val context: Context,
    private var data: List<InspireEntity>,
    private val onclick: (Long) -> Unit = {}
) : PagerAdapter() {
    override fun getCount(): Int = data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ItemMainPagerBinding = ItemMainPagerBinding.inflate(LayoutInflater.from(context), container, false)
        binding.mainShared.setOnClickListener {
            onclick.invoke(data[position].currentTime)
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