package com.eagskunst.apps.logindemo.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import timber.log.Timber

/**
 * Created by eagskunst in 7/6/2020.
 */
class LoginRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun loginUser(info: UserCredentials): LoginViewState {
        val (email, password) = info

        return try {
            val data = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Success(data.user)
        } catch (e: Exception) {
            Timber.e(e.cause)
            Error("Authentication failed", e)
        }
    }

    fun logoutUser(): LoginViewState {
        firebaseAuth.signOut()
        return Initial
    }
}