package org.sopt.at.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopt.at.core.state.UiState
import org.sopt.at.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Sign-in 관련 부수효과를 정의합니다.
 */
sealed class SignInSideEffect {
    data class ShowSnackbar(val message: String) : SignInSideEffect()
}

private const val MIN_ID_LENGTH = 6
private const val MIN_PASSWORD_LENGTH = 8

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignInSideEffect>()
    val sideEffect: SharedFlow<SignInSideEffect> = _sideEffect.asSharedFlow()

    fun updateUserId(id: String) {
        val updatedState = _state.value.copy(userId = id)
        _state.value = updatedState.copy(
            isInputValid = validateInput(updatedState.userId, updatedState.password)
        )
    }

    fun updatePassword(password: String) {
        val updatedState = _state.value.copy(password = password)
        _state.value = updatedState.copy(
            isInputValid = validateInput(updatedState.userId, updatedState.password)
        )
    }

    private fun validateInput(id: String, password: String): Boolean {
        return id.length >= MIN_ID_LENGTH && 
               password.length >= MIN_PASSWORD_LENGTH &&
               password.any { it.isDigit() } &&
               password.any { it.isLetter() }
    }

    fun signIn() {
        if (!_state.value.isInputValid) {
            viewModelScope.launch {
                _sideEffect.emit(SignInSideEffect.ShowSnackbar("아이디는 최소 6자, 비밀번호는 8자 이상 (숫자와 문자 포함) 입력해주세요"))
            }
            return
        }
        
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            try {
                val storedId = userRepository.getUserId().first()
                val storedPassword = userRepository.getUserPassword().first()

                if (storedId == null || storedPassword == null) {
                    _uiState.value = UiState.Failure("저장된 사용자 정보가 없습니다. 회원가입이 필요합니다.")
                    _sideEffect.emit(SignInSideEffect.ShowSnackbar("저장된 사용자 정보가 없습니다. 회원가입이 필요합니다."))
                    return@launch
                }

                if (_state.value.userId == storedId && _state.value.password == storedPassword) {
                    _state.value = _state.value.copy(isSignInSuccessful = true)
                    _uiState.value = UiState.Success(Unit)
                    _sideEffect.emit(SignInSideEffect.ShowSnackbar("로그인에 성공했습니다."))
                } else {
                    _uiState.value = UiState.Failure("아이디 또는 비밀번호가 일치하지 않습니다.")
                    _sideEffect.emit(SignInSideEffect.ShowSnackbar("아이디 또는 비밀번호가 일치하지 않습니다."))
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Failure("로그인 중 오류가 발생했습니다: ${e.message}")
                _sideEffect.emit(SignInSideEffect.ShowSnackbar("로그인 중 오류가 발생했습니다: ${e.message}"))
            }
        }
    }

    fun resetSignInState() {
        _state.value = _state.value.copy(isSignInSuccessful = false)
        _uiState.value = UiState.Empty
    }
}
