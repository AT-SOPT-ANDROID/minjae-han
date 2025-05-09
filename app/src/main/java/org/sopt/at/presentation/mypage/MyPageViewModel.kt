package org.sopt.at.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.at.domain.repository.DataStoreRepository
import org.sopt.at.domain.repository.UserRepository

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            userRepository.getUserNickName()
                .onSuccess { response ->
                    _userId.value = response.nickname
                }
                .onFailure {
                    _userId.value = "불러오기 실패임"
                }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            dataStoreRepository.setAutoLogin(false)
            _userId.value = null
        }
    }
}
