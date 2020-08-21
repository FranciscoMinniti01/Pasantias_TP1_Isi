package com.example.smart_things.Class

import java.io.Serializable

data class ConnecData(
    var ip: String? = null,
    var password:String? = null,
    var user:String? = null) : Serializable