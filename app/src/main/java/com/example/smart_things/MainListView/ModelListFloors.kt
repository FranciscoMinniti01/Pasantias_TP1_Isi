package com.example.smart_things.MainListView

class ModelListFloors (var Floors0: String? = null,
                       var Floors1: String? = null,
                       var Floors2: String? = null,
                       var Floors3: String? = null,
                       var Floors4: String? = null){

    fun getFloors(I:Int): String? {
        return when (I) {
            0 -> {
                return Floors0
            }
            1 -> {
                return Floors1
            }
            2 -> {
                return Floors2
            }
            3 -> {
                return Floors3
            }
            4 -> {
                return Floors4
            }
            else -> {
                return null.toString()
            }
        }
    }

    fun setFloors(I:Int, Floor:String){
        return when (I) {
            0 -> {
                Floors0 = Floor
            }
            1 -> {
                Floors1 = Floor
            }
            2 -> {
                Floors2 = Floor
            }
            3 -> {
                Floors3 = Floor
            }
            4 -> {
                Floors4 = Floor
            }
            else -> {

            }
        }
    }

}