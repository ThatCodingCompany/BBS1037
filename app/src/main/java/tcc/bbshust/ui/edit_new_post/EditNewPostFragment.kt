package tcc.bbshust.ui.edit_new_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import tcc.bbshust.R
import tcc.bbshust.databinding.EditNewPostFragmentBinding

class EditNewPostFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    lateinit var binding: EditNewPostFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.edit_new_post_fragment, container, false)
        return binding.root
    }
}