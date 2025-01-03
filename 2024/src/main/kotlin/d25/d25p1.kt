package d25

import utils.*

fun main() {
    val grid = read3dCharGrid("/d25.txt")

    val locks = grid.filter{ it[0].all{ it == '#' } }
    val keys = grid.filter{ it[it.size-1].all{ it == '#' } }

    var x = 0L
    for (lock in locks) {
        for (key in keys) {
            if (!overlaps(lock, key)) {
                x++
            }
        }
    }

    println(x)


}

fun overlaps(lock: List<List<Char>>, key: List<List<Char>>): Boolean {
    val lockHeights = toHeights(lock)
    val keyHeights = toHeights(key)

    val overlaps = lockHeights.zip(keyHeights).any { it.first + it.second > 7 }
    return overlaps
}

fun toHeights(thing: List<List<Char>>): List<Int> {
    val heights = mutableListOf(0, 0, 0, 0, 0)
    for (row in thing) {
        for (idx in row.indices) {
            if (row[idx] == '#') heights[idx]++
        }
    }
    return heights
}
