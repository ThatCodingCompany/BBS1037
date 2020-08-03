package tcc.bbshust.ui.postdetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.R
import tcc.bbshust.databinding.PostDetailFragmentBinding
import tcc.bbshust.ui.reply.ReplyFragment

class PostDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PostDetailFragment()
    }

    private lateinit var viewModel: PostDetailViewModel
    private lateinit var binding: PostDetailFragmentBinding
    private lateinit var adapter: DetailAdapter

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

        binding.floatingReplyButton.setOnClickListener {
            childFragmentManager.beginTransaction().add(R.id.fragmentForReply,replyFragment).commit()
            it.visibility=View.GONE
        }
        return binding.root
    }

}