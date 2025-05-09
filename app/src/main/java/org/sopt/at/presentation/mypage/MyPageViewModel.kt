package org.sopt.at.presentation.mypage

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sopt.at.core.state.UiState
import org.sopt.at.domain.entity.UserEntity
import org.sopt.at.domain.repository.UserRepository

sealed class MyPageSideEffect {
    data class ShowSnackbar(val message: String) : MyPageSideEffect()
}

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId.asStateFlow()
    
    private val _nickname = MutableStateFlow<String?>(null)
    val nickname: StateFlow<String?> = _nickname.asStateFlow()
    
    private val _uiState = MutableStateFlow<UiState<UserEntity>>(UiState.Empty)
    val uiState: StateFlow<UiState<UserEntity>> = _uiState.asStateFlow()
    
    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect: SharedFlow<MyPageSideEffect> = _sideEffect.asSharedFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            userRepository.getUserId().collectLatest { userId ->
                _userId.value = userId
                if (userId != null) {
                    fetchUserInfo()
                }
            }
        }
    }
    
    private fun fetchUserInfo() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                userRepository.getUserInfo().onSuccess { userEntity ->
                    _nickname.value = userEntity.nickname
                    _uiState.value = UiState.Success(userEntity)
                }.onFailure { throwable ->
                    _uiState.value = UiState.Failure(throwable.message ?: "사용자 정보를 불러오는데 실패했습니다.")
                    _sideEffect.emit(MyPageSideEffect.ShowSnackbar(throwable.message ?: "사용자 정보를 불러오는데 실패했습니다."))
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Failure("사용자 정보를 불러오는데 실패했습니다: ${e.message}")
                _sideEffect.emit(MyPageSideEffect.ShowSnackbar("사용자 정보를 불러오는데 실패했습니다: ${e.message}"))
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userRepository.setAutoLogin(false)
            _userId.value = null
            _nickname.value = null
        }
    }
}
