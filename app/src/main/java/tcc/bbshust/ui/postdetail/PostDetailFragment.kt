package tcc.bbshust.ui.postdetail

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.post_detail_fragment.*
import tcc.bbshust.MainActivity
import tcc.bbshust.R
import tcc.bbshust.databinding.PostDetailFragmentBinding
import tcc.bbshust.ui.reply.ReplyFragment

class PostDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PostDetailFragment()
        val TAG = "PostDetailFragment:"
    }

    private lateinit var viewModel: PostDetailViewModel
    private lateinit var binding: PostDetailFragmentBinding
    private lateinit var adapter: DetailAdapter

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.post_detail_fragment, container, false)

        val args = PostDetailFragmentArgs.fromBundle(requireArguments())
        val factory = PostDetailViewModelFactory(args.tokenData, args.postId)
        viewModel = ViewModelProvider(this, factory).get(PostDetailViewModel::class.java)

        adapter = DetailAdapter(args.postId)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.replyRecyclerView.adapter = adapter

        viewModel.replyList.observe(viewLifecycleOwner, Observer {
            val idMap = mutableMapOf<String, Int>()
            var i = 0
            for (e in it) {
                if (idMap.containsKey(e.author)) {
                    e.author = String.format(" 0X%04X ", idMap[e.author])
                    continue
                }

                idMap.put(e.author, i)
                e.author = String.format(" 0X%04X ", i)
                i += 1
            }
            adapter.submitList(it)
        })


        val replyFragment = ReplyFragment.newInstance()

        childFragmentManager.setFragmentResultListener(
            "requestKey",
            viewLifecycleOwner,
            FragmentResultListener { key, bundle ->
                val result = bundle.getString(key)
                viewModel.addReply(result!!)
            }
        )

        binding.floatingReplyButton.setOnClickListener {
            childFragmentManager.beginTransaction().add(R.id.fragmentForReply, replyFragment)
                .commit()
            binding.fragmentForReply.visibility = View.VISIBLE
            it.visibility = View.GONE
        }

        viewModel.isReplied.observe(viewLifecycleOwner, Observer {
            if (it) {
                replyFragment.viewModel.content.value = ""
                replyFragment.viewModel.content.value = null
                childFragmentManager.beginTransaction().remove(replyFragment).commit()
                binding.fragmentForReply.visibility = View.GONE
                binding.floatingReplyButton.visibility = View.VISIBLE
                viewModel.getPostDetail()
            }
        })
        return binding.root
    }


}