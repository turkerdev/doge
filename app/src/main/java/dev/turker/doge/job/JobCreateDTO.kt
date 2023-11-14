package dev.turker.doge.job

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobCreateDTO(
    @SerialName("kopek_adi")
    val kopekAdi: String,
    @SerialName("kopek_cinsi")
    val kopekCinsi: String,
    @SerialName("aciklama")
    val aciklama: String,
    @SerialName("user_id")
    val userid:String
)