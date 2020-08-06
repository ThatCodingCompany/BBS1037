package tcc.bbshust.ui.message

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import tcc.bbshust.R
import tcc.bbshust.databinding.HomeFragmentBinding
import tcc.bbshust.databinding.MessageFragmentBinding
import tcc.bbshust.ui.home.HomeViewModel

class MessageFragment : Fragment() {


    private lateinit var viewModel: MessageViewModel
    private lateinit var binding: MessageFragmentBinding

    val TAG="MessageFragment :"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel= ViewModelProvider(this).get(MessageViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.message_fragment, container, false)

        binding.lifecycleOwner=this
        binding.viewModel=viewModel

        Log.d(TAG, "onCreateView: set")

        val navView = binding.navView
        val navController = findNavController()
        navView.setupWithNavController(navController)

        return binding.root
    }



}