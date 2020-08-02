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
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DetailDiffCallback()) {


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).id) {
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
                val item = getItem(position) as DataItem.ReplyItem
                holder.bind(item.post)
            }
            is DetailViewHolder -> {
                val item = getItem(position) as DataItem.ReplyItem
                holder.bind(item.post)
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

    class DetailViewHolder(private val binding: PostHeaderBinding) :
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


class DetailDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class DataItem {
    data class ReplyItem(val post: Post) : DataItem() {
        override val id = post.postId
    }

    abstract val id: String
}