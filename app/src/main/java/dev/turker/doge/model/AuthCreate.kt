package dev.turker.doge.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthCreate(
    val name: String,
    val surname: String,
    val photo: String,
    val phone: String,
)