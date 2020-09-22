package com.boltic28.networkretroroom.data.dto.apiresponse.human

import com.boltic28.networkretroroom.data.dto.apiresponse.human.location.Coordinates
import com.boltic28.networkretroroom.data.dto.apiresponse.human.location.Street
import com.boltic28.networkretroroom.data.dto.apiresponse.human.location.Timezone

data class Location(
 val street: Street,
 val city: String,
 val state: String,
 val country: String,
 val postcode: Int,
 val coordinates: Coordinates,
 val timezone: Timezone
)