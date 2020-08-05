package tcc.bbshust.ui.edit_new_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tcc.bbshust.MainActivity
import tcc.bbshust.R
import tcc.bbshust.databinding.EditNewPostFragmentBinding

class EditNewPostFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    lateinit var binding: EditNewPostFragmentBinding
    lateinit var viewModel: EditNewPostViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.edit_new_post_fragment, container, false)

        val arguments = EditNewPostFragmentArgs.fromBundle(requireArguments())
        val token = arguments.token

        val application = requireNotNull(this.activity).application
        val editNewPostViewModelFactory = EditNewPostViewModelFactory(application, token)
        viewModel = ViewModelProvider(
            this,
            editNewPostViewModelFactory
        ).get(EditNewPostViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                this.findNavController()
                    .navigate(EditNewPostFragmentDirections.actionEditNewPostFragmentToHomeFragment())
                viewModel.doneNavigatingToHome()
            }
        })

        viewModel.makeToast.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                viewModel.doneMakingToast()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val father=activity as MainActivity
        father.bottomViewStateChange(View.GONE)
    }
}