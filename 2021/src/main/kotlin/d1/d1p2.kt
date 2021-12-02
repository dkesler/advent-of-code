package d1

import utils.readLongList

fun main() {
    val list = readLongList("/d1/d1p1")

    val foldedList = mutableListOf<Long>()

    for (i in 0..list.size-3) {
        foldedList.add(list[i] + list[i+1] + list[i+2])
    }

    var incs = 0
    for (i in 0..foldedList.size-2) {
        if (foldedList[i+1] > foldedList[i]) incs++
    }

    println(incs)
}
