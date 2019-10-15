package com.dailyquest.utils

import android.content.Context

class SessionManager(private val context: Context) {
    val sharedPreferences =
        context.getSharedPreferences(Constants.SHAREDPREFERENCES, Context.MODE_PRIVATE)

    fun setSession(uidParent: String?, role: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.PREF_PARENT_UID, uidParent)
        editor.putString(Constants.PREF_ROLE, role)
        editor.apply()
    }

    fun getParentUid(): String? {
        return sharedPreferences.getString(Constants.PREF_PARENT_UID, null)
    }

    fun getRole(): String{
        return sharedPreferences.getString(Constants.PREF_ROLE, Constants.ANAK)
    }

    fun clear(){
        sharedPreferences.edit().clear().commit()
    }
}