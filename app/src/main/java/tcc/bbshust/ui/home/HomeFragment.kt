package tcc.bbshust.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.R
import tcc.bbshust.databinding.HomeFragmentBinding
import tcc.bbshust.network.data.Post

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        var i = 1
        val l = mutableListOf<Post>()
        while (i <= 30) {
            val tp = Post(
                i.toString(),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                "Title$i",
                "Author$i",
                "Content$i",
                listOf<Post>()
            )
            l.add(tp)
            i += 1
        }

        postAdapter = PostAdapter(PostListener {
            Log.d(TAG, "onCreateView: item in RECYCLERVIEW clicked.")
        })

        binding.postRecyclerview.adapter = postAdapter

        /*viewModel.postList.observe(viewLifecycleOwner, Observer { list ->
            postAdapter.submitList(list)
        })*/

        postAdapter.submitList(l)

        return binding.root
    }


}