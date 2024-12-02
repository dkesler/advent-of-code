package d2

import utils.*

fun isSafe2(list: List<Long>): Boolean {
    for (i in list.indices) {
        val rmList = list.filterIndexed{idx, _ -> idx != i}
        if (isSafe(rmList)) return true
    }
    return false
}

fun main() {
    val lines = readLongGrid("/d2.txt", " ")

    println(lines.count{ isSafe2(it) })
}