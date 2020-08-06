package tcc.bbshust.utils

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat

fun makeToken(_token: String) = "Bearer $_token"


@SuppressLint("SimpleDateFormat")
fun Long.toTimeString(): String {
    val thisMillis = this * 1000L
    return SimpleDateFormat("M月d日HH:mm")
        .format(thisMillis).toString()
}


@BindingAdapter("concise_title")
fun TextView.setConciseTitle(title: String) {
    val result = if (title.length > 10) {
        title.slice(IntRange(0, 9)) + "..."
    } else {
        title
    }
    this.text = result
}

@BindingAdapter("set_text_time")
fun TextView.setTime(time: Long) {
    this.text = time.toTimeString()
}

@BindingAdapter("set_create_time")
fun TextView.setCreateTime(time: Long) {
    val text = "发帖时间：" + time.toTimeString()
    this.text = text
}

@BindingAdapter("set_update_time")
fun TextView.setUpdateTime(time: Long) {
    val text = "最新回复：" + time.toTimeString()
    this.text = text
}

class TimeCount(millisInFuture: Long, countDownInterval: Long, val button: Button) : CountDownTimer(
    millisInFuture,
    countDownInterval
) {
    override fun onFinish() {
        button.text = "重新发送"
        button.isClickable = true
    }

    override fun onTick(millisUntilFinished: Long) {
        button.isClickable = false
        val hint = (millisUntilFinished / 1000).toString() + "s"
        button.text = hint
    }

}