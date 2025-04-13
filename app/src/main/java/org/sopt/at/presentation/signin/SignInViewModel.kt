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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.at.core.state.UiState
import org.sopt.at.domain.repository.UserRepository
import javax.inject.Inject

sealed class SignInSideEffect {
    data class ShowSnackbar(val message: String) : SignInSideEffect()
}

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState>
        get() = _state.asStateFlow()
    
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val uiState: StateFlow<UiState<Unit>> = _uiState
    
    private val _sideEffect = MutableSharedFlow<SignInSideEffect>()
    val sideEffect: SharedFlow<SignInSideEffect> = _sideEffect.asSharedFlow()
    
    fun updateUserId(id: String) {
        _state.update { currentState ->
            currentState.copy(
                userId = id,
                isInputValid = validateInput(id, currentState.password)
            )
        }
    }
    
    fun updatePassword(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password,
                isInputValid = validateInput(currentState.userId, password)
            )
        }
    }
    
    private fun validateInput(id: String, password: String): Boolean {
        return id.isNotBlank() && password.isNotBlank()
    }
    
    fun signIn() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            runCatching {
                val storedId = userRepository.getUserId().first()
                val storedPassword = userRepository.getUserPassword().first()
                
                if (storedId == _state.value.userId && storedPassword == _state.value.password) {
                    Result.success(Unit)
                } else {
                    Result.failure(IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."))
                }
            }.fold(
                onSuccess = {
                    _state.update { currentState ->
                        currentState.copy(isSignInSuccessful = true)
                    }
                    _uiState.value = UiState.Success(Unit)
                    _sideEffect.emit(SignInSideEffect.ShowSnackbar("로그인에 성공했습니다."))
                },
                onFailure = { throwable ->
                    _state.update { currentState ->
                        currentState.copy(isSignInSuccessful = false)
                    }
                    _uiState.value = UiState.Failure(throwable.message ?: "로그인 중 오류가 발생했습니다.")
                    _sideEffect.emit(SignInSideEffect.ShowSnackbar(throwable.message ?: "로그인 중 오류가 발생했습니다."))
                }
            )
        }
    }
    
    fun resetSignInState() {
        _state.update { currentState ->
            currentState.copy(
                isSignInSuccessful = false
            )
        }
        _uiState.value = UiState.Empty
    }
}
