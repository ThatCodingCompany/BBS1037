package tcc.bbshust.register


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import tcc.bbshust.R
import tcc.bbshust.databinding.FragmentRegisterBinding
import tcc.bbshust.utils.TimeCount


class RegisterFragment : Fragment() {

    private val TAG = "RegisterFragment :"

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.toastInfo.observe(viewLifecycleOwner, Observer {
            it.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.registerRes.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                viewModel.toastInfo.value = "验证码发送成功！"
                Log.d(TAG, "onCreateView: isSuccess")
            } else {
                viewModel.toastInfo.value = "error:${it.hint}"
                Log.d(TAG, "onCreateView: notSuccess")
            }
        })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            it.let {
                if (it.isSuccess) {
                    this.findNavController()
                        .navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                    viewModel.navigateToHomeDone()
                    viewModel.toastInfo.value = "嘻嘻嘻，注册好啦~"
                } else {
                    viewModel.toastInfo.value = "error:${it.hint}"
                }
            }
        })

        binding.getCodeButton.setOnClickListener {
            if (viewModel.email.value != null && viewModel.email.value != "") {
                val timeCount = TimeCount(60000, 1000, it as Button)
                timeCount.start()
                viewModel.getRegisterRes()
            } else {
                viewModel.toastInfo.value = "邮箱不能为空！"
            }
        }

        return binding.root
    }


}