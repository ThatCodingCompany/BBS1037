package tcc.bbshust.ui.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tcc.bbshust.network.data.TokenData

class PostDetailViewModelFactory(
    private val token: TokenData,
    private val postId: String
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostDetailViewModel::class.java)) {
            return PostDetailViewModel(token, postId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}