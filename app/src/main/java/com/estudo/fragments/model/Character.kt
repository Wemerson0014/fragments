package com.estudo.fragments.model

import java.io.Serializable

data class Character (
    val name: String,
    val description: String,
    val imageResId: Int
) : Serializable