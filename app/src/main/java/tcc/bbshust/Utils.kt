package tcc.bbshust

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun Long.toTimeString(): String {
    return SimpleDateFormat("HH:mm")
        .format(this).toString()
}

@BindingAdapter("concise_content")
fun TextView.setConciseContent(content: String) {
    val result = if (content.length > 20) {
        content.slice(IntRange(0, 19)) + "......"
    } else {
        content
    }
    this.text = result
}

@BindingAdapter("set_text_time")
fun TextView.setTime(time: Long) {
    this.text = time.toTimeString()
}
