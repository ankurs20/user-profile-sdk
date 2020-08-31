package com.poc.user_profile_sdk.common

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class UriUtility {

    companion object {

        /*
        * The method extracts URI query parameters into a hashmap.
        * @param uri The URI from which the query params are to be extracted
        * @return Returns a hashmap with query params as key => value or nil if no query params found.
        * */
        fun queryParams(uri: String): HashMap<String, String>?{
            // split the uri on ?, query param string will be placed at index 1.
            val splittedUri = uri.split("?").toTypedArray()
            if (splittedUri.size > 1){
                val queryParam = splittedUri[1] // query param string.
                // Split on & to separate out each param=value into an array.
                val params = queryParam.split("&")
                val qParams = HashMap<String, String>()
                for (p in params){
                    // Extract individual query param into a hashmap like param=>value
                    if (p.split("=").size > 1){
                        qParams.set(p.split("=")[0], p.split("=")[1])
                    }
                }
                return qParams
            }
            return null //  return null if no query param found.
        }

    }
}