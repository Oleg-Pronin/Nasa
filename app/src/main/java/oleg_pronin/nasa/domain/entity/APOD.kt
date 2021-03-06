package oleg_pronin.nasa.domain.entity

import com.google.gson.annotations.SerializedName

data class APOD(
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("hdurl") val hdurl: String?,
    @field:SerializedName("media_type") val mediaType: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("copyright") val copyright: String?
)
