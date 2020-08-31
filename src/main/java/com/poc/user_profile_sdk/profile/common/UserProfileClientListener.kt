package com.poc.user_profile_sdk.profile.common

import android.content.Context

interface UserProfileClientListener {

    fun onResultReceived(identifier: Int, isSuccessful: Boolean, context: Context?, data: HashMap<String, String>?)
    fun openCameraActivity(context: Context?)

}