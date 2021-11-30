package com.karta

import android.content.Context

class PreferenceUtil(
    private val context: Context
) {

    private val sharedPreference by lazy {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setStatus(isLogin: Boolean) {
        sharedPreference.edit().putBoolean(LOGIN_STATUS, isLogin).apply()
    }

    fun getStatus(): Boolean =
        sharedPreference.getBoolean(LOGIN_STATUS, false)


    companion object {
        private const val PREFERENCE_NAME = "karta_pref"
        private const val LOGIN_STATUS = "login_status"
    }
}
