package dev.turker.doge.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    @SerialName("created_at") val createdAt: String,
    val name: String,
    val surname: String,
    val photo: String,
    val phone: String,
)