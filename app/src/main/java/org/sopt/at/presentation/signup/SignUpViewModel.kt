package org.sopt.at.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.at.core.state.UiState
import org.sopt.at.core.util.validation.SoptValidator
import org.sopt.at.domain.repository.DataStoreRepository
import org.sopt.at.presentation.signup.model.SignUpState

sealed class SignUpSideEffect {
    data class ShowSnackbar(val message: String) : SignUpSideEffect()
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState>
        get() = _state.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val uiState: StateFlow<UiState<Unit>>
        get() = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignUpSideEffect>()
    val sideEffect: SharedFlow<SignUpSideEffect> 
        get() = _sideEffect.asSharedFlow()

    fun updateUserId(id: String) {
        _state.update { currentState ->
            currentState.copy(
                userId = id
            )
        }
    }

    fun updatePassword(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun isValidUserId(id: String): Boolean {
        return id.isNotBlank() && id.length in 6..12 && SoptValidator.isUserIdFormat(id)
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length in 8..15 && SoptValidator.isPasswordFormat(password)
    }

    fun saveUserCredentials() {
        viewModelScope.launch {
            if (!isValidUserId(_state.value.userId) || !isValidPassword(_state.value.password)) {
                _sideEffect.emit(SignUpSideEffect.ShowSnackbar("아이디 또는 비밀번호가 유효하지 않습니다."))
                return@launch
            }

            _uiState.value = UiState.Loading

            try {
                dataStoreRepository.saveUserCredentials(
                    id = _state.value.userId,
                    password = _state.value.password
                )

                _state.update { currentState ->
                    currentState.copy(isSignUpComplete = true)
                }
                _uiState.value = UiState.Success(Unit)
                _sideEffect.emit(SignUpSideEffect.ShowSnackbar("회원가입이 완료되었습니다."))
            } catch (e: Exception) {
                _uiState.value = UiState.Failure("회원가입 중 오류가 발생했습니다.")
                _sideEffect.emit(SignUpSideEffect.ShowSnackbar("회원가입 중 오류가 발생했습니다."))
            }
        }
    }

    fun resetSignUpState() {
        _state.update { currentState ->
            currentState.copy(isSignUpComplete = false)
        }
        _uiState.value = UiState.Empty
    }
}
