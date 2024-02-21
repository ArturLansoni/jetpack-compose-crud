package com.arturlansoni.servicelist.presentation.ui.register

import com.arturlansoni.servicelist.core.enums.ScreenStatus

data class RegisterModel (
    val status: ScreenStatus = ScreenStatus.Initial,
    val errorMessage: String? = null
)