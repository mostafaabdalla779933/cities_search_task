package com.moveandstore.citiessearchtask.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    @SerialName("_id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("country") val country: String,
    @SerialName("coord") val coord: Coord
)


@Serializable
data class Coord(
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double
)

