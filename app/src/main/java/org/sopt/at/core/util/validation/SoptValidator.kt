package org.sopt.at.core.util.validation

object SoptValidator {
    private val userIdPattern = "^[a-z0-9]+$".toRegex()
    private val passwordPattern = "^[A-Za-z0-9~!@#$%^&*()_+\\-=\\[\\]{}|;:'\",.<>/?]+$".toRegex()
    private val nicknamePattern = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]+$".toRegex()

    fun isPasswordFormat(input: String): Boolean {
        return input.matches(passwordPattern)
    }

    fun isUserIdFormat(input: String): Boolean {
        return input.matches(userIdPattern)
    }

    fun isNicknameFormat(input: String): Boolean {
        return input.matches(nicknamePattern)
    }
}
