package com.eagskunst.apps.logindemo.data

import com.google.firebase.auth.FirebaseUser
import kotlin.Exception

/**
 * Created by eagskunst in 7/6/2020.
 */

sealed class LoginViewState

object Initial : LoginViewState()
data class Success(val firebaseUser: FirebaseUser?): LoginViewState()
object Loading: LoginViewState()
data class Fail(val msg: String, val exception: Exception): LoginViewState()