package com.dailyquest.model

data class UserModel(
    var fullName: String = "",
    var role: String = "",
    var token: String? = null
)