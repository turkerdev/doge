package dev.turker.doge.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comms(
    val id: Int,
    @SerialName("created_at") val createdAt: String,
    val post: Int,
    val sender: String,
    val receiver: String,
    val message: String
)