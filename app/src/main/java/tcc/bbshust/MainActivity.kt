package tcc.bbshust

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import tcc.bbshust.databinding.ActivityMainBinding
import tcc.bbshust.login.LoginFragment
import tcc.bbshust.login.LoginViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val TAG = "Main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navView = binding.navView
        val navController = findNavController(R.id.navHostFragment)

        navView.setupWithNavController(navController)

    }

    fun bottomViewStateChange(state:Int){
        binding.navView.visibility=state
    }
}