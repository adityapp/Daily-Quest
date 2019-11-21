package com.dailyquest.model

import java.io.Serializable

data class ChildrenModel(
    var uid: String? = null,
    var fullName: String = "",
    var role: String = "",
    var reward: Int = 0,
    var quest: HashMap<String, QuestModel> = hashMapOf(),
    var token: String? = null
) : Serializable