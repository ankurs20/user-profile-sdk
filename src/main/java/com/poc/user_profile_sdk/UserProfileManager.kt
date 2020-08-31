package com.poc.user_profile_sdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.poc.user_profile_sdk.common.*
import com.poc.user_profile_sdk.profile.activity.WebViewActivity
import com.poc.user_profile_sdk.profile.model.Err
import com.poc.user_profile_sdk.profile.model.UserProfile

class UserProfileManager(context: Context){

    var context: Context
    lateinit var broadCastReceiver: BroadcastReceiver

    var success: ((UserProfile) -> Unit)? = null
    var failure: ((Err) -> Unit)? = null

    init {
        this.context = context
    }

    // presents create profile page
    fun showCreateUserProfile(context: Context){
        setupBroadcastListener(context)
        val activity = WebViewActivity()
        val intent = Intent(context, activity::class.java)
        intent.putExtra(constUrl, constUserProfileHTMLPath)
        context.startActivity(intent)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            removeBroadcastListener()
        }
    }

    fun handleSuccess(profile: UserProfile){
        success?.let { it(profile) }
    }

    fun handleFailure(error: Err){
        failure?.let { it(error) }
    }

    // sets up broadcast listeners
    fun setupBroadcastListener(context: Context){
        broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {

                val obj = intent?.getSerializableExtra("data")
                when (intent?.action) {
                    BROADCAST_CREATE_PROFILE_SUCCESS -> handleSuccess(obj as UserProfile)
                    BROADCAST_CREATE_PROFILE_FAILURE -> handleFailure(obj as Err)
                    BROADCAST_CREATE_PROFILE_DONE -> createProfileDone()
                }
            }
        }
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_CREATE_PROFILE_SUCCESS))
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_CREATE_PROFILE_FAILURE))
        LocalBroadcastManager.getInstance(context).registerReceiver(broadCastReceiver, IntentFilter(
            BROADCAST_CREATE_PROFILE_DONE)
        )

    }

    fun createProfileDone(){
        removeBroadcastListener()
    }

    fun removeBroadcastListener(){
        LocalBroadcastManager.getInstance(context)
            .unregisterReceiver(broadCastReceiver)
    }

}