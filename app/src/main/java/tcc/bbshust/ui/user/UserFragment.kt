package tcc.bbshust.ui.user

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.R
import tcc.bbshust.databinding.UserFragmentBinding
import tcc.bbshust.ui.message.MessageViewModel

class UserFragment : Fragment() {


    private lateinit var viewModel: UserViewModel
    private lateinit var binding:UserFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false)

        binding.lifecycleOwner=this
        binding.viewModel=viewModel

        return binding.root
    }


}