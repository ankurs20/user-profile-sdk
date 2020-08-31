package com.poc.user_profile_sdk.profile.model

import java.io.Serializable

class UserProfile: Serializable {
    var base64ImageData: String? = null// holds base 64 image  data.
    var name: String? = null
    var email: String? = null
    var phone: String? = null
}