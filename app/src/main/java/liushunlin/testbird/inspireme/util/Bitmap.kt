package liushunlin.testbird.inspireme.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import java.io.File
import java.io.FileOutputStream

fun viewToBitmap(context: Context, view: View, currentTime: Long): String {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE)
    view.draw(canvas)
    return saveBitmapToAlbum(context, bitmap,currentTime)
}

fun saveBitmapToAlbum(context: Context, bitmap: Bitmap, currentTime: Long): String {
    val dir = File(context.externalCacheDir, "image")
    if (!dir.exists()) {
        dir.mkdir()
    }
    val fileName = "${currentTime}.jpg"
    val file = File(dir, fileName)
    if (file.isFile && file.exists()) {
        return file.path
    }
    val fileOutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()
    MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, fileName, null)
    context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(file.path))))
    return file.path
}

fun Bitmap.blurBitmap(context: Context): Bitmap? {
    return FastBlurUtil.toBlur(this,8)
}