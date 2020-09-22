package com.boltic28.networkretroroom.data.dto.apiresponse

import com.boltic28.networkretroroom.data.dto.apiresponse.human.*

data class PostHuman(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Dob,
    val phone: String,
    val cell: String,
    val id: ID,
    val picture: Picture,
    val nat: String
)