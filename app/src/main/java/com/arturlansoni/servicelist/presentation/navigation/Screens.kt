package com.arturlansoni.servicelist.presentation.navigation

sealed class Screens(
    val route: String,
){
    object Login: Screens("login")
    object Register: Screens("register")
    object List: Screens("list")
    object Form: Screens("form")
}