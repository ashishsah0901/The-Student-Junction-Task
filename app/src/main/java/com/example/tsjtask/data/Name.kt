package com.example.tsjtask.data

import java.io.Serializable

data class Name(
    val first: String?,
    val last: String?,
    val title: String?
) : Serializable