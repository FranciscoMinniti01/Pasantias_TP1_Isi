package com.example.smart_things.Class

import java.io.Serializable

class ModelUser (var Name:String? = null,
                 var Email:String? = null,
                 var Password:String? = null,
                 var saveAcount:Boolean = false,
                 var Room:String? = null) : Serializable