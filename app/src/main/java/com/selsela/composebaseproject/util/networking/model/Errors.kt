package com.selsela.composebaseproject.util.networking.model

import androidx.annotation.Keep

@Keep
data class Errors(
    val error: String,
    val `field`: String
)