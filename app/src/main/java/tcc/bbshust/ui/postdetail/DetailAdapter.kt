package tcc.bbshust.ui.postdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tcc.bbshust.databinding.PostDetailItemViewBinding
import tcc.bbshust.databinding.PostHeaderBinding
import tcc.bbshust.network.data.Post


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class DetailAdapter(val postId: String) :
    ListAdapter<Post, RecyclerView.ViewHolder>(DetailDiffCallback()) {


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).postId) {
//            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
//            is DataItem.ReplyItem -> ITEM_VIEW_TYPE_ITEM
            postId -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> DetailViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ReplyViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReplyViewHolder -> {
                val item = getItem(position)
                holder.bind(item)
            }
            is DetailViewHolder -> {
                val item = getItem(position)
                holder.bind(item)
            }

        }
    }

    class ReplyViewHolder private constructor(private val binding: PostDetailItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.post = item
        }

        companion object {
            fun from(parent: ViewGroup): ReplyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostDetailItemViewBinding.inflate(layoutInflater, parent, false)
                return ReplyViewHolder(binding)
            }
        }
    }

    class DetailViewHolder private constructor(private val binding: PostHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.post = item
        }

        companion object {
            fun from(parent: ViewGroup): DetailViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostHeaderBinding.inflate(layoutInflater, parent, false)
                return DetailViewHolder(binding)
            }
        }
    }

}


class DetailDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}

