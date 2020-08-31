package com.poc.user_profile_sdk.profile.common

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.poc.user_profile_sdk.common.*
import java.lang.ref.WeakReference
import java.net.URLDecoder


class ProfileWebViewClient: WebViewClient() {

    var listener: WeakReference<UserProfileClientListener>? = null
    var tag: Int = 0

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val uriString = request?.url.toString()
        if (uriString != null){
            val queryParams = UriUtility.queryParams(uriString)
            if (queryParams != null) {
                if (queryParams.containsKey("takePhoto")){
                    // open camera
                    openCamera(view?.context)
                }else {
                    handleQueryParams(true, view?.context, queryParams)
                }
                return true
            }
        }
        handleQueryParams(false, view?.context, null)
        return true
    }

    fun handleQueryParams(isSuccessful: Boolean, context: Context?, params: HashMap<String, String>?) {

        if (isSuccessful){
            // validate params here. and give the result back to UserProfileManager
            val name = params?.get(constKeyName)
            val email = params?.get(constKeyEmail)
            val mobile = params?.get(constKeyMobile)

            val decodedName: String = URLDecoder.decode(name?:"", "UTF-8")
            val decodedEmail: String = URLDecoder.decode(email?:"", "UTF-8")
            val decodedMobile: String = URLDecoder.decode(mobile?:"", "UTF-8")

            var result = HashMap<String, String>()
            if (!Utility.isValidName(decodedName)){
                result.set(constKeyError, ErrMsgInvalidName)
                this.listener?.get()?.onResultReceived(tag, false, context, result)
            }else if(!Utility.isValidEmail(decodedEmail)){
                result.set(constKeyError, ErrMsgInvalidEmail)
                this.listener?.get()?.onResultReceived(tag, false, context, result)
            }else if(!Utility.isValidPhone(decodedMobile)){
                result.set(constKeyError, ErrMsgInvalidMobile)
                this.listener?.get()?.onResultReceived(tag, false, context, result)
            }else{
                result.set(constKeyName, decodedName)
                result.set(constKeyEmail, decodedEmail)
                result.set(constKeyMobile, decodedMobile)
                this.listener?.get()?.onResultReceived(tag, true, context, result)
            }
        }
    }

    fun openCamera(context: Context?){
        this.listener?.get()?.openCameraActivity(context)
    }

}