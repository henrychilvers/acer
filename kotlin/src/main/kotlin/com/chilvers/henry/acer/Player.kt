package com.chilvers.henry.acer

data class Player(val position: Int, val name: String) {
    var daysToChoose: MutableList<Int> = mutableListOf()

    fun finalResults(): String {
        return daysToChoose
            .sorted()
            .joinToString("," )
    }
}