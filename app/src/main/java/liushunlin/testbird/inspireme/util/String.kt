package liushunlin.testbird.inspireme.util

import android.content.Context
import android.widget.Toast

fun String?.toast(context: Context){
    if (this == null)
        return
    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
}