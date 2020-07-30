package tcc.bbshust.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.R
import tcc.bbshust.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding:HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel=ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        binding.lifecycleOwner=this
        binding.viewModel=viewModel

        return binding.root
    }



}