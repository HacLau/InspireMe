package liushunlin.testbird.inspireme.util

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import java.io.File
import java.io.FileOutputStream

fun viewToBitmap(activity: Activity, view: View):String {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE)
    view.draw(canvas)
    return saveBitmapToAlbum(activity, bitmap)
}

fun saveBitmapToAlbum(activity: Activity, bitmap: Bitmap):String {
    val dir = File(activity.externalCacheDir, "image")
    if (!dir.exists()) {
        dir.mkdir()
    }
    val fileName = "${getToday()}.jpg"
    val file = File(dir, fileName)
    if (file.isFile && file.exists()){
        return file.path
    }
    val fileOutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()
    MediaStore.Images.Media.insertImage(activity.contentResolver, file.absolutePath, fileName, null)
    activity.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(file.path))))
    return file.path
}