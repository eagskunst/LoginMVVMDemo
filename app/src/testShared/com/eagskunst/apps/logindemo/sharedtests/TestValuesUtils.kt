package com.eagskunst.apps.logindemo

import androidx.annotation.VisibleForTesting
import com.eagskunst.apps.logindemo.data.UserCredentials

/**
 * Created by eagskunst in 7/6/2020.
 */
object TestValuesUtils {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    val validCredentials = UserCredentials("logindemo@yopmail.com", "demo12345")
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    val invalidCredentials = UserCredentials("logindemo@yopmail.com", "demo1234")

}