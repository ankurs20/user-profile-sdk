# user-profile-sdk
The project provides an Android library. It provides a webview based create profile page. User can enter the profile details and submit. On success, the details will be provided, or otherwise an error will be returned.


### Integration Guide:

1. Add "user-profile-sdk-release.aar" file into the app>libs directory. It is located in binary directory.
2. In app Gradle file write this
````
repositories {
    flatDir {
        dirs 'libs'
    }
}
dependencies {
    implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'
    implementation (name: 'user-profile-sdk-release', ext:'aar')
}
````


### Usage Create Profile:
````
val profileMgr = UserProfileManager(this)

profileMgr.success = { profile ->
    Toast.makeText(this, "User successfully created.", Toast.LENGTH_LONG).show()
}
profileMgr.failure = { error ->
    Toast.makeText(this, error.reason, Toast.LENGTH_SHORT).show()
}
profileMgr.showCreateUserProfile(this)
````
