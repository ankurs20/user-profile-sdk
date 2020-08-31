package com.poc.user_profile_sdk.common

import com.poc.user_profile_sdk.profile.common.ProfileWebViewClient
import com.poc.user_profile_sdk.profile.common.UserProfileClientListener
import java.lang.ref.WeakReference

class ObjectFactory {

    companion object {

        fun getProfileWebViewClient(tag: Int, listener: UserProfileClientListener): ProfileWebViewClient {
            val wvClient = ProfileWebViewClient()
            wvClient.tag = 1
            wvClient.listener = WeakReference(listener)
            return wvClient
        }
    }
}