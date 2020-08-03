package tcc.bbshust.ui.reply

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import tcc.bbshust.R
import tcc.bbshust.databinding.ReplyFragmentBinding

class ReplyFragment : Fragment() {

    companion object {
        fun newInstance() = ReplyFragment()
    }

    private lateinit var viewModel: ReplyViewModel
    private lateinit var binding:ReplyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.reply_fragment,container,false)




        return binding.root

    }


}