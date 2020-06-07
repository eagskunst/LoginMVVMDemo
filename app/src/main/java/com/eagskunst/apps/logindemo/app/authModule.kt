package com.eagskunst.apps.logindemo.app

import com.eagskunst.apps.logindemo.data.AuthRepository
import com.eagskunst.apps.logindemo.ui.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by eagskunst in 7/6/2020.
 */

val authModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { AuthRepository(get()) }
    viewModel { AuthViewModel(get()) }
}