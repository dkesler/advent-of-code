package d2

import utils.*
import kotlin.math.abs

fun isSafe(list: List<Long>): Boolean {
    if (list[0] == list[1]) return false
    val ascending = list[0] < list[1]
    for (i in (0 until list.size-1)) {
        if (ascending && list[i] >= list[i+1]) return false
        if (!ascending && list[i] <= list[i+1]) return false
        val diff = abs(list[i] - list[i + 1])
        if (diff > 3) return false
    }
    return true
}

fun main() {
    val lines = readLongGrid("/d2.txt", " ")

    println(lines.count{ isSafe(it) })
}