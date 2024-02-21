package com.arturlansoni.servicelist.core.enums

sealed class ScreenStatus {
    object Initial : ScreenStatus()
    object Loading : ScreenStatus()
    object Success : ScreenStatus()
    data class Error(val message: String) : ScreenStatus()
}