package tcc.bbshust.ui.postdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.R
import tcc.bbshust.databinding.PostDetailFragmentBinding

class PostDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PostDetailFragment()
    }

    private lateinit var viewModel: PostDetailViewModel
    private lateinit var binding: PostDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.post_detail_fragment,container,false)

        val args=PostDetailFragmentArgs.fromBundle(requireArguments())
        val factory=PostDetailViewModelFactory(args.tokenData)
        viewModel=ViewModelProvider(this,factory).get(PostDetailViewModel::class.java)

        binding.lifecycleOwner=this

        return binding.root
    }



}