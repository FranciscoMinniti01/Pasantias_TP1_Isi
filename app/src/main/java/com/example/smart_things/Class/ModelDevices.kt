package com.example.smart_things.Class

import java.io.Serializable

data class ModelDevices(var Room:String? = null,
                        var Floor:String? = null,
                        var Name:String? = null,
                        var Type: String? = null,
                        var State:Boolean = false) : Serializable