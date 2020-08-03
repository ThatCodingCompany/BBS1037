package tcc.bbshust.ui.reply

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.R
import tcc.bbshust.databinding.ReplyFragmentBinding

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
                parentFragmentManager.setFragmentResult("requestKey",
                    bundleOf("requestKey" to viewModel.content.value))
            } else {
                Toast.makeText(context, "内容不能为空！", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root

    }


}