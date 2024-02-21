package com.arturlansoni.servicelist.presentation.ui.login

import androidx.lifecycle.ViewModel
import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.core.infra.auth.AuthClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authClient: AuthClient) : ViewModel() {

    private val _state = MutableStateFlow(LoginModel())
    val state = _state.asStateFlow<LoginModel>()

    suspend fun onSubmit(email: String, password: String) {
        if (_state.value.status is ScreenStatus.Loading) return
        updateStatus(ScreenStatus.Loading)
        val (error, _) = authClient.signIn(email = email, password = password)
        if (error.message != null) {
            updateStatus(ScreenStatus.Error(error.message))
        } else {
            updateStatus(ScreenStatus.Success)
        }
    }

    fun getUserIsLoggedIn(): Boolean = authClient.userIsLoggedIn()

    private fun updateStatus(status: ScreenStatus) = _state.update {
        it.copy(
            status = status
        )
    }
}
