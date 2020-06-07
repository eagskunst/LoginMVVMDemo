package com.eagskunst.apps.logindemo.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eagskunst.apps.logindemo.data.*
import com.eagskunst.apps.logindemo.ui.AuthViewModel
import com.eagskunst.apps.logindemo.getOrAwaitValue
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import com.eagskunst.apps.logindemo.TestValuesUtils.invalidCredentials
import com.eagskunst.apps.logindemo.TestValuesUtils.validCredentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule

/**
 * Created by eagskunst in 7/6/2020.
 */
@ExperimentalCoroutinesApi
class AuthViewModelTests {

    private lateinit var mockUser: FirebaseUser
    lateinit var viewModel: AuthViewModel
    lateinit var expectedFail: Fail
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("aThread")

    @Before
    fun setup() {
        val repo = mockk<AuthRepository>()
        mockUser = mockk<FirebaseUser>()
        expectedFail = Fail("Auth failed",
            Exception("Auth failed"))

        every { mockUser.displayName } returns "Emmanuel"

        coEvery { repo.signInUser(validCredentials) } returns Success(mockUser)
        coEvery { repo.signInUser(invalidCredentials) } returns expectedFail
        coEvery { repo.signOutUser() } returns Initial
        every { repo.currentUser() } returns null

        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = AuthViewModel(repo)
    }

    @Test
    fun assertInitialState_IsInitial() {
        val expectedState = Initial
        val actualState= viewModel.loginState.getOrAwaitValue()
        assertThat<LoginViewState>(actualState, CoreMatchers.`is`(expectedState))
    }

    @Test
    fun assertState_validCredentials_isLoading(){
        val expectedState = Loading
        viewModel.signInUser(validCredentials.email, validCredentials.password)
        val actualState= viewModel.loginState.getOrAwaitValue()
        assertThat<LoginViewState>(actualState, CoreMatchers.`is`(expectedState))

        viewModel.signOutUser()
    }

    @Test
    fun assertState_invalidCredentials_IsError() {
        val expectedState = expectedFail
        viewModel.signInUser(invalidCredentials.email, invalidCredentials.password)
        val actualState= viewModel.loginState.getOrAwaitValue(latchCount = 2)
        assertThat<LoginViewState>(actualState, CoreMatchers.`is`(expectedState))
    }

    @Test
    fun assertState_validCredentials_IsSuccess() {
        val expectedState = Success(mockUser)
        viewModel.signInUser(validCredentials.email, validCredentials.password)
        val actualState= viewModel.loginState.getOrAwaitValue(latchCount = 2)
        assertThat<LoginViewState>(actualState, CoreMatchers.`is`(expectedState))
    }

    @Test
    fun assertState_afterSignOut_IsInitial() {
        val expectedState = Initial
        viewModel.signOutUser()
        val actualState= viewModel.loginState.getOrAwaitValue()
        assertThat<LoginViewState>(actualState, CoreMatchers.`is`(expectedState))
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

}