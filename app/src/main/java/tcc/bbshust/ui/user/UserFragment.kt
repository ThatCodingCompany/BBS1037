package tcc.bbshust.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tcc.bbshust.MainActivity
import tcc.bbshust.R
import tcc.bbshust.database.AccountDatabase
import tcc.bbshust.databinding.UserFragmentBinding

class UserFragment : Fragment() {


    private lateinit var viewModel: UserViewModel
    private lateinit var binding: UserFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = AccountDatabase.getInstance(application).accountDatabaseDao
        val factory = UserViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.button.setOnClickListener {
            this.findNavController()
                .navigate(UserFragmentDirections.actionUserFragmentToLoginFragment())

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val father = activity as MainActivity
        father.bottomViewStateChange(View.VISIBLE)
    }
}