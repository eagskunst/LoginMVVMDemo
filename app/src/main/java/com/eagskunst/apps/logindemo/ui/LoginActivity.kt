package com.eagskunst.apps.logindemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.eagskunst.apps.logindemo.databinding.ActivityLoginBinding
import com.eagskunst.apps.logindemo.utils.textString

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.usernameEt.doAfterTextChanged { changeButtonState() }
        binding.passwordEt.doAfterTextChanged { changeButtonState() }
    }

    private fun changeButtonState() {
        binding.loginBtn.isEnabled = binding.usernameEt.textString().isNotEmpty() &&
                binding.passwordEt.textString().isNotEmpty()
    }
}
