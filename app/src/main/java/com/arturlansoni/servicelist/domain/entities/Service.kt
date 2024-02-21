package com.arturlansoni.servicelist.domain.entities

data class Service(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val rating: Double = 0.0,
    val category: String = "",
    val imageURL: String = "",
)