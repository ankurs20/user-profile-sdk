package com.poc.user_profile_sdk.profile.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.poc.user_profile_sdk.R
import com.poc.user_profile_sdk.common.*
import com.poc.user_profile_sdk.profile.common.UserProfileClientListener
import com.poc.user_profile_sdk.profile.model.Err
import com.poc.user_profile_sdk.profile.model.UserProfile
import java.io.ByteArrayOutputStream
import java.util.*


class WebViewActivity : AppCompatActivity(), UserProfileClientListener {

    lateinit var webView: WebView
    var profile = UserProfile()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        val uriString = intent.getStringExtra(constUrl)
        webView.webViewClient =  ObjectFactory.getProfileWebViewClient(1, this)
        webView.loadUrl(uriString)
    }

    override fun onResultReceived(
        identifier: Int,
        isSuccessful: Boolean,
        context: Context?,
        data: HashMap<String, String>?
    ) {
        if (identifier == 1){
            if (context != null) {
                if (isSuccessful && !TextUtils.isEmpty(profile.base64ImageData)) {
                    // save profile
                    profile.name = data?.get(constKeyName).toString()
                    profile.email = data?.get(constKeyEmail).toString()
                    profile.phone = data?.get(constKeyMobile).toString()

                    val intent = Intent(BROADCAST_CREATE_PROFILE_SUCCESS)
                    intent.putExtra("data", profile)
                    LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
                    this.finish()
                } else {
                    // report error
                    var err = Err()
                    if (TextUtils.isEmpty(profile.base64ImageData)){
                        err.reason = ErrMsgInvalidImage
                    }else {
                        err.reason = data?.get(constKeyError).toString()
                    }
                    val intent = Intent(BROADCAST_CREATE_PROFILE_FAILURE)
                    intent.putExtra("data", err)
                    LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
                }
            }
        }
    }

    override fun openCameraActivity(context: Context?){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, CameraRequest)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraRequest && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
            val encoded = Base64.getEncoder().encodeToString(byteArray)
            var name = StringBuffer("setPhoto(\"")
            name.append(encoded)
            name.append("\")")
            webView.evaluateJavascript(name.toString(),  null)
            profile.base64ImageData = encoded
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(BROADCAST_CREATE_PROFILE_DONE)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}