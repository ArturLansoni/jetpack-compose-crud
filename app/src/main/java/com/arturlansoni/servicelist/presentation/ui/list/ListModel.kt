package com.arturlansoni.servicelist.presentation.ui.list


import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.domain.entities.Service

data class ListModel (
    val status: ScreenStatus = ScreenStatus.Initial,
    val errorMessage: String? = null,
    val services: List<Service> = listOf()
)