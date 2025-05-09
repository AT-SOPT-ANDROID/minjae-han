package org.sopt.at.presentation.signin

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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopt.at.core.state.UiState
import org.sopt.at.domain.repository.AuthRepository
import org.sopt.at.domain.repository.DataStoreRepository
import org.sopt.at.presentation.signin.model.SignInState

sealed class SignInSideEffect {
    data class ShowSnackbar(val message: String) : SignInSideEffect()
}

private const val MIN_ID_LENGTH = 8
private const val MIN_PASSWORD_LENGTH = 8

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val uiState: StateFlow<UiState<Unit>> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SignInSideEffect>()
    val sideEffect: SharedFlow<SignInSideEffect> = _sideEffect.asSharedFlow()

    private val _autoLoginChecked = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            checkAutoLogin()
        }
    }

    private suspend fun checkAutoLogin() {
        runCatching {
            val storedId = dataStoreRepository.getUserId().first()
            val storedPassword = dataStoreRepository.getUserPassword().first()
            val autoLoginEnabled = dataStoreRepository.isAutoLoginEnabled().first()

            if (storedId != null && storedPassword != null && autoLoginEnabled == true) {
                _state.value = _state.value.copy(
                    userId = storedId,
                    password = storedPassword,
                    isInputValid = validateInput(storedId, storedPassword)
                )
                _autoLoginChecked.value = true
                signIn(isAutoLogin = true)
            }
        }.onFailure {
            _sideEffect.emit(SignInSideEffect.ShowSnackbar("자동 로그인 실패 아마 토큰 만료겠죠뭐"))
        }
    }

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
        return id.length >= MIN_ID_LENGTH && password.length >= MIN_PASSWORD_LENGTH
    }

    fun signIn() {
        signIn(isAutoLogin = false)
    }

    private fun signIn(isAutoLogin: Boolean) {
        if (!_state.value.isInputValid && !isAutoLogin) {
            viewModelScope.launch {
                _sideEffect.emit(SignInSideEffect.ShowSnackbar("아이디는 최소 6자, 비밀번호는 8자 이상 (숫자와 문자 포함) 입력해주세요"))
            }
        }

        viewModelScope.launch {
            if (!isAutoLogin) {
                _uiState.value = UiState.Loading
            }

            runCatching {
                authRepository.signIn(_state.value.userId, _state.value.password)
            }.onSuccess { result ->
                result.fold(
                    onSuccess = {
                        _state.value = _state.value.copy(isSignInSuccessful = true)
                        _uiState.value = UiState.Success(Unit)

                        dataStoreRepository.saveUserCredentials(_state.value.userId, _state.value.password)
                        
                        if (!isAutoLogin) {
                            dataStoreRepository.setAutoLogin(true)
                            _sideEffect.emit(SignInSideEffect.ShowSnackbar("로그인에 성공했습니다."))
                        }
                    },
                    onFailure = { error ->
                        if (!isAutoLogin) {
                            _uiState.value = UiState.Failure("로그인 실패: ${error.message}")
                            _sideEffect.emit(SignInSideEffect.ShowSnackbar("로그인에 실패했습니다: ${error.message}"))
                        }
                    }
                )
            }.onFailure { exception ->
                if (!isAutoLogin) {
                    _uiState.value = UiState.Failure("로그인 중 오류 발생: ${exception.message}")
                    _sideEffect.emit(SignInSideEffect.ShowSnackbar("로그인 중 오류가 발생했습니다: ${exception.message}"))
                }
            }
        }
    }

    fun resetSignInState() {
        _state.value = _state.value.copy(isSignInSuccessful = false)
        _uiState.value = UiState.Empty
    }
}
