package com.example.smart_things.MainListView

class ModelListRooms(
    var Room0: String? = null,
    var Room1: String? = null,
    var Room2: String? = null,
    var Room3: String? = null,
    var Room4: String? = null,
    var Room5: String? = null,
    var Room6: String? = null,
    var Room7: String? = null,
    var Room8: String? = null,
    var Room9: String? = null) {

    fun getRooms(I:Int): String? {
        return when (I) {
            0 -> {
                return Room0
            }
            1 -> {
                return Room1
            }
            2 -> {
                return Room2
            }
            3 -> {
                return Room3
            }
            4 -> {
                return Room4
            }
            4 -> {
                return Room5
            }
            4 -> {
                return Room6
            }
            4 -> {
                return Room7
            }
            4 -> {
                return Room8
            }
            4 -> {
                return Room9
            }
            else -> {
                return null.toString()
            }
        }
    }
    fun setRooms(I:Int,Room:String) {
        return when (I) {
            0 -> {
                 Room0 = Room
            }
            1 -> {
                Room1 = Room
            }
            2 -> {
                Room2 = Room
            }
            3 -> {
                Room3 = Room
            }
            4 -> {
                Room4 = Room
            }
            5 -> {
                Room5 = Room
            }
            6 -> {
                Room6 = Room
            }
            7 -> {
                Room7 = Room
            }
            8 -> {
                Room8 = Room
            }
            9 -> {
                Room9 = Room
            }
            else -> {

            }
        }
    }
}