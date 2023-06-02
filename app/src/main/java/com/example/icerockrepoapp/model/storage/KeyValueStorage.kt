package com.example.icerockrepoapp.model.storage

import android.content.Context
import android.content.SharedPreferences

class KeyValueStorage (context: Context) {


    object KEYS{
        const val NAME_KEY = "KEY_VALUE_STORAGE"
        const val NAME_TOKEN = "TOKEN"
        const val NAME_USERNAME = "USERNAME"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(KEYS.NAME_KEY, Context.MODE_PRIVATE)

    var token: String? = null
        set(value) {
            saveString(value, KEYS.NAME_TOKEN)
            field = value
        }
        get() = sharedPreferences.getString(KEYS.NAME_TOKEN, "")
    var user: String? = null
        set(value) {
            saveString(value, KEYS.NAME_USERNAME)
            field = value
        }
        get() = sharedPreferences.getString(KEYS.NAME_USERNAME, "")

    private fun saveString(value: String?, key: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}