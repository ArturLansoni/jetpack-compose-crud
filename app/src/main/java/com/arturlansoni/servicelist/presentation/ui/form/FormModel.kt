package com.arturlansoni.servicelist.presentation.ui.form

import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.domain.entities.Service

data class FormModel (
    val status: ScreenStatus = ScreenStatus.Initial,
    val errorMessage: String? = null,
    val service: Service? = null
)