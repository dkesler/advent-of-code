package d1

import utils.readLongList

fun main() {
    val list = readLongList("/d1/d1p1")
    var incs = 0
    for (i in 0..list.size-2) {
        if (list[i+1] > list[i]) incs++
    }

    println(incs)
}
