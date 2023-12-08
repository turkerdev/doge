package dev.turker.doge.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommsCreate(
    @SerialName("post") val post: Int,
    @SerialName("receiver") val receiver: String,
    @SerialName("message") val message: String
)