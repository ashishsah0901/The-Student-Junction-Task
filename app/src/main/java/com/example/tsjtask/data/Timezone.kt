package com.example.tsjtask.data

import java.io.Serializable

data class Timezone(
    val description: String?,
    val offset: String?
) : Serializable