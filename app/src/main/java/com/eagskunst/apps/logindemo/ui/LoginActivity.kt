package com.eagskunst.apps.logindemo.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.eagskunst.apps.logindemo.data.*
import com.eagskunst.apps.logindemo.databinding.ActivityLoginBinding
import com.eagskunst.apps.logindemo.utils.textString
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.usernameEt.doAfterTextChanged { changeButtonState() }
        binding.passwordEt.doAfterTextChanged { changeButtonState() }

        binding.loginBtn.setOnClickListener {
            viewModel.signInUser(
                email = binding.usernameEt.textString(),
                password = binding.passwordEt.textString()
            )
        }

        binding.signOutBtn.setOnClickListener {
            viewModel.signOutUser()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loginState.observe(this, Observer { state ->
            state?.let { render(it) }
        })
    }

    private fun render(viewState: LoginViewState) {
        when (viewState) {
            Initial ->  {
                binding.welcome.text = ""
                binding.signOutBtn.visibility = View.GONE
            }
            is Error ->  {
                Snackbar.make(binding.root, viewState.msg, Snackbar.LENGTH_SHORT).show()
            }
            Loading -> binding.progressBar.visibility = View.VISIBLE
            is Success -> {
                binding.welcome.text = "Welcome ${viewState.firebaseUser?.email}"
                binding.signOutBtn.visibility = View.VISIBLE
            }
        }

        if (viewState !is Loading){
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun changeButtonState() {
        binding.loginBtn.isEnabled = binding.usernameEt.textString().isNotEmpty() &&
                binding.passwordEt.textString().isNotEmpty()
    }
}
