package com.example.mobg5_53204.fragments.login


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobg5_53204.MainActivity
import com.example.mobg5_53204.R
import com.example.mobg5_53204.databinding.ActivityLoginBinding
import com.example.mobg5_53204.model.user.UserSession

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginActivityViewModel
    private lateinit var viewModelFactory: LoginActivityViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // Initialize ViewModel and Factory
        viewModelFactory = LoginActivityViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginActivityViewModel::class.java]

        // Observe and Populate email list
        viewModel.populateEmail()
        viewModel.data.observe(this, Observer { data ->
            val adapter = ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                data
            )
            binding.emailTxt.setAdapter(adapter)
        })

        // Verify Email and Navigate to HomeFragment
        binding.loginBtn.setOnClickListener {
            viewModel.emailCheck(binding.emailTxt.text.toString())
            if (viewModel.status.value == true) {
                UserSession.userEmail = binding.emailTxt.text.toString()

                finish()

                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            }
        }

        // Observe and Show Toast Messages
        viewModel.status.observe(this, Observer { status ->
            status?.let {
                if (status == true) {
                    Toast.makeText(
                        applicationContext,
                        "valid email address",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "${binding.emailTxt.text} is an invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
