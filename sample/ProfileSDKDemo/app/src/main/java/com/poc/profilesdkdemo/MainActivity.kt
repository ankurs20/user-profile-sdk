package com.poc.profilesdkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.poc.user_profile_sdk.UserProfileManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val profileMgr = UserProfileManager(this)

        profileMgr.success = { profile ->
            Toast.makeText(this, "User successfully created.", Toast.LENGTH_LONG).show()
        }
        profileMgr.failure = { error ->
            Toast.makeText(this, error.reason, Toast.LENGTH_SHORT).show()
        }
        profileMgr.showCreateUserProfile(this)
    }
}