package com.boltic28.cbook.data

class Process(val name: String, val id: Long, val timer: Int) {
    var now: Int = 0

    fun left() = timer - now
}