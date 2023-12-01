package dev.turker.doge.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCreate(
    @SerialName("kopek_adi") val dogName: String,
    @SerialName("kopek_cinsi") val dogRace: String,
    @SerialName("aciklama") val description: String,
    @SerialName("user_id") val userId: String
)