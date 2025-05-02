package org.sopt.at.core.util.validation

object SoptValidator {
    private val userIdPattern = "^[a-z0-9]+$".toRegex()
    private val passwordPattern = "^[A-Za-z0-9~!@#$%^&*()_+\\-=\\[\\]{}|;:'\",.<>/?]+$".toRegex()

    fun isPasswordFormat(input: String): Boolean {
        return input.matches(passwordPattern)
    }

    fun isUserIdFormat(input: String): Boolean {
        return input.matches(userIdPattern)
    }
}
