package com.arturlansoni.servicelist.presentation.ui.register

import androidx.lifecycle.ViewModel
import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.core.infra.auth.AuthClient
import com.arturlansoni.servicelist.presentation.ui.login.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authClient: AuthClient) : ViewModel() {

    private val _state = MutableStateFlow(RegisterModel())
    val state = _state.asStateFlow()

    suspend fun onSubmit(email: String, password: String) {
        updateStatus(ScreenStatus.Loading)
        val (error, _) = authClient.createUser(email = email, password = password)
        if (error.message != null) {
            updateStatus(ScreenStatus.Error(error.message))
        } else {
            updateStatus(ScreenStatus.Success)
        }
    }
    private fun updateStatus(status: ScreenStatus) = _state.update {
        it.copy(
            status = status
        )
    }
}
