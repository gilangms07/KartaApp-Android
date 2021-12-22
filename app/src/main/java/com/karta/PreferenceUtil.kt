package com.karta

import android.content.Context

class PreferenceUtil(
    private val context: Context
) {

    private val sharedPreference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setStatus(isLogin: Boolean) {
        sharedPreference.edit().putBoolean(LOGIN_STATUS, isLogin).apply()
    }

    fun isAlreadyLogin(): Boolean =
        sharedPreference.getBoolean(LOGIN_STATUS, false)

    fun setEmail(email: String) {
        sharedPreference.edit().putString(USER_EMAIL, email).apply()
    }

    fun getEmail() : String =
            sharedPreference.getString(USER_EMAIL, "")!!


    companion object {
        private const val PREFERENCE_NAME = "karta_pref"
        private const val LOGIN_STATUS = "login_status"
        private const val USER_EMAIL = "user_email"
    }
}
