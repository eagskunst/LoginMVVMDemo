package com.eagskunst.apps.logindemo.data

import android.text.TextUtils
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import com.eagskunst.apps.logindemo.TestValuesUtils.invalidCredentials
import com.eagskunst.apps.logindemo.TestValuesUtils.validCredentials

/**
 * Created by eagskunst in 7/6/2020.
 */
class AuthRepositoryTest {

    lateinit var repository: AuthRepository
    lateinit var expectedException: FirebaseAuthException

    @Before
    fun setup() {
        val auth = mockk<FirebaseAuth>()
        val authResult = mockk<AuthResult>()
        val validTask = Tasks.forResult(authResult)

        mockkStatic(TextUtils::class)
        every { TextUtils.isEmpty(any()) }.answers {
            arg<String>(0).isEmpty()
        }
        expectedException = FirebaseAuthException("Auth failed", "Auth failed")

        every { authResult.user } returns null
        val exceptionTask = Tasks.forException<AuthResult>(expectedException)

        every { auth.signInWithEmailAndPassword(validCredentials.email,
            validCredentials.password) } returns validTask

        every { auth.signInWithEmailAndPassword(invalidCredentials.email,
            invalidCredentials.password) } returns exceptionTask


        every { auth.signOut() } returns Unit


        repository = AuthRepository(auth)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_signIn_validCredentials() {
        val expectedState = Success(null)
        runBlockingTest {
            val actualState = repository.signInUser(validCredentials)
            assertThat<LoginViewState>(actualState, `is`(expectedState))
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun test_signIn_invalidCredentials() {
        runBlockingTest {
            val expectedState = Fail(expectedException.message ?: "", expectedException)
            val actualState = repository.signInUser(invalidCredentials)
            assertThat<LoginViewState>(actualState, `is`(expectedState))
        }
    }

}