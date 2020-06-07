package com.eagskunst.apps.logindemo.app

import com.eagskunst.apps.logindemo.data.LoginRepository
import com.eagskunst.apps.logindemo.ui.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by eagskunst in 7/6/2020.
 */

val loginModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { LoginRepository(get()) }
    viewModel { LoginViewModel(get()) }
}