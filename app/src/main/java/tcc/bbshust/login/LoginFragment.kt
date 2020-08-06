package tcc.bbshust.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentController
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tcc.bbshust.MainActivity
import tcc.bbshust.R
import tcc.bbshust.database.AccountDatabase
import tcc.bbshust.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {


    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    val TAG = "LoginFragment :"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = AccountDatabase.getInstance(application).accountDatabaseDao
        val factory = LoginViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.buttonLogin.setOnClickListener {
            if (viewModel.username.value != null && viewModel.username.value != ""
                && viewModel.password.value != null && viewModel.password.value != ""
            ) {
                viewModel.loginForToken()
            } else {
                Toast.makeText(context, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginRegisterButton.setOnClickListener {
            this.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.isSuccess) {
                    this.findNavController()
                        .navigate(
                            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        )
                    viewModel.navigateToHomeDone()
                    Toast.makeText(context, "啦啦啦，登录成功！", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "error:${it.hint}", Toast.LENGTH_LONG).show()
                }
            }
        })

        return binding.root
    }


}