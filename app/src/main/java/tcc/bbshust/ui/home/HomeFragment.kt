package tcc.bbshust.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import tcc.bbshust.MainActivity
import tcc.bbshust.R
import tcc.bbshust.database.AccountDatabase
import tcc.bbshust.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = AccountDatabase.getInstance(application).accountDatabaseDao

        val factory = HomeViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.postRecyclerview.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        postAdapter = PostAdapter(PostListener { postId ->
            viewModel.onPostClicked(postId)
            Log.d(TAG, "onCreateView: item in RECYCLERVIEW clicked.")
        })

        binding.postRecyclerview.adapter = postAdapter

        viewModel.postList.observe(viewLifecycleOwner, Observer { list ->
            postAdapter.submitList(list.sortedByDescending { post -> post.updateTime })
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                viewModel.doneNavigatingToLogin()
            }
        })

        viewModel.navigateToEditNewPost.observe(viewLifecycleOwner, Observer { mToken ->
            if (mToken != null) {
                this.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToEditNewPostFragment(mToken))
                viewModel.doneNavigatingToEditNewPost()
            }
        })

        viewModel.makeToast.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.doneMakingToast()
            }
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { postId ->
            if (postId != null) {
                Log.d(TAG, "onCreateView: ${postId}")
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPostDetailFragment(
                        viewModel.token!!,
                        postId
                    )
                )
                viewModel.doneNavigateToDetail()
            }
        })

        val navView = binding.navView
        val navController = findNavController()
        navView.setupWithNavController(navController)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fillPostList()
    }

}