package com.poc.user_profile_sdk.common

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


class Utility {

    companion object {

        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        fun isValidPhone(target: CharSequence?): Boolean{
            return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches()
        }

        fun isValidName(target: CharSequence?): Boolean{
            if (TextUtils.isEmpty(target)){
                return false
            }
            val inputStr: CharSequence = target!!
            val pattern: Pattern = Pattern.compile("^[a-zA-Z\\s]*$")
            val matcher: Matcher = pattern.matcher(inputStr)
            return matcher.matches()
        }
    }
}