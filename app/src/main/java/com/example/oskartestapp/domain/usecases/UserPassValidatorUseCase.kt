package com.example.oskartestapp.domain.usecases

import com.example.oskartestapp.common.ErrorResource
import com.example.oskartestapp.common.Resource
import java.util.regex.Pattern
import javax.inject.Inject

class UserPassValidatorUseCase @Inject constructor() {

    operator fun invoke(
        email: String,
        password: String,
    ): Resource<Boolean> {
        return if (validateEmail(email).not())
            Resource.Error(ErrorResource.VALIDATION)
        else if (password.length < 4)
            Resource.Error(ErrorResource.VALIDATION)
        else Resource.Success(true)

    }

}


private fun validateEmail(string: String): Boolean {
    return Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    ).matcher(string)
        .matches()
}