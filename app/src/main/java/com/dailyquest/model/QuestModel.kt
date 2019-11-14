package com.dailyquest.model

import com.dailyquest.utils.Constants

data class QuestModel(
    var id: String? = null,
    var title: String = "",
    var description: String = "",
    var startTime: Long = 0,
    var endTime: Long = 0,
    var createdAt: Long = 0,
    var reward: Int = 0,
    var hideReward: Boolean = false,
    var status: String = Constants.STATUS_OPEN,
    var opened: Boolean = false
)