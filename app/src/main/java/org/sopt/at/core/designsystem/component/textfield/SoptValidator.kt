package org.sopt.at.core.designsystem.component.textfield

object SoptValidator {
    private val userIdPattern = "^[a-z0-9]+$".toRegex()
    private val passwordPattern = "^[A-Za-z0-9~!@#$%^&*]+$".toRegex()

    fun isPasswordFormat(input: String): Boolean {
        return input.isEmpty() || input.matches(passwordPattern)
    }

    fun isUserIdFormat(input: String): Boolean {
        return input.isEmpty() || input.matches(userIdPattern)
    }
}
