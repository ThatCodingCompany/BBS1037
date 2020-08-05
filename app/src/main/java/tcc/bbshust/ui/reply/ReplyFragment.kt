package tcc.bbshust.ui.reply

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.R
import tcc.bbshust.databinding.ReplyFragmentBinding
import java.util.*
import kotlin.concurrent.timerTask


class ReplyFragment : Fragment() {

    companion object {
        fun newInstance() = ReplyFragment()
    }

    lateinit var viewModel: ReplyViewModel
    lateinit var binding: ReplyFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.reply_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ReplyViewModel::class.java)
        binding.viewModel = viewModel

        binding.replyButton.setOnClickListener {
            if (viewModel.content.value != null && viewModel.content.value != "") {
                parentFragmentManager.setFragmentResult(
                    "requestKey",
                    bundleOf("requestKey" to viewModel.content.value)
                )
            } else {
                Toast.makeText(context, "内容不能为空！", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root


    }


}