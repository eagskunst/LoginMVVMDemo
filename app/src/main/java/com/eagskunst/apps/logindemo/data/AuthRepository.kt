package com.eagskunst.apps.logindemo.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import timber.log.Timber

/**
 * Created by eagskunst in 7/6/2020.
 */
class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    suspend fun signInUser(info: UserCredentials): LoginViewState {
        val (email, password) = info

        return try {
            val data = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Success(data.user)
        } catch (e: Exception) {
            Timber.d("Sign in error: ${e.message}")
            if (e is FirebaseAuthException) {
                Fail(e.message ?: "Authentication failed", e)
            }
            else {
                Fail("Authentication failed", e)
            }
        }
    }

    fun signOutUser(): LoginViewState {
        firebaseAuth.signOut()
        return Initial
    }

    fun currentUser() = firebaseAuth.currentUser
}