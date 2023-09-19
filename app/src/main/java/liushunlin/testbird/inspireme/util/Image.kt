package liushunlin.testbird.inspireme.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by Administrator on 2018/1/13.
 */
object ImageUtils {
    //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
    fun layoutView(v: View, width: Int, height: Int) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height)
        val measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST)
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight)
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
    }

    fun viewSaveToImage(view: View, child: String): String {
        Log.e("ssh", "a")
        /**
         * View组件显示的内容可以通过cache机制保存为bitmap
         * 我们要获取它的cache先要通过setDrawingCacheEnable方法把cache开启，
         * 然后再调用getDrawingCache方法就可 以获得view的cache图片了
         * 。buildDrawingCache方法可以不用调用，因为调用getDrawingCache方法时，
         * 若果 cache没有建立，系统会自动调用buildDrawingCache方法生成cache。
         * 若果要更新cache, 必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         */
        //    view.setDrawingCacheEnabled(true);
        //    view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        //设置绘制缓存背景颜色
        //    view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        val cachebmp = loadBitmapFromView(view)

//    aaa.setImageBitmap(cachebmp);//直接展示转化的bitmap

        //保存在本地 产品还没决定要不要保存在本地
        val fos: FileOutputStream
        try {
            // 判断手机设备是否有SD卡
            val isHasSDCard = Environment.getExternalStorageState() ==
                    Environment.MEDIA_MOUNTED
            fos = if (isHasSDCard) {
                // SD卡根目录
                val sdRoot = Environment.getExternalStorageDirectory()
                Log.e("ssh", sdRoot.toString())
                val file = File(sdRoot, "$child.png")
                FileOutputStream(file)
            } else throw Exception("创建文件失败!")
            //压缩图片 30 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        view.destroyDrawingCache()
        return sharePic(cachebmp, child)
    }

    private fun loadBitmapFromView(v: View): Bitmap {
        val w = v.width
        val h = v.height
        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        /** 如果不设置canvas画布为白色，则生成透明  */
//    c.drawColor(Color.WHITE);
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }

    //保存在本地并一键分享
    private fun sharePic(cachebmp: Bitmap?, child: String): String {
        val qrImage = File(Environment.getExternalStorageDirectory(), "$child.jpg")
        if (qrImage.exists()) {
            qrImage.delete()
        }
        try {
            qrImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var fOut: FileOutputStream? = null
        try {
            fOut = FileOutputStream(qrImage)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        if (cachebmp == null) {
            return ""
        }
        cachebmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
        try {
            fOut!!.flush()
            fOut.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //    Toast.makeText(this, "保存成功 " + qrImage.getPath().toString(), Toast.LENGTH_SHORT).show();
        return qrImage.path
    }
}