package d14

import utils.readList
import java.lang.Integer.min
import kotlin.math.max


fun main() {
    val lines = readList("/d14.txt")

    val rocks = mutableSetOf<Pair<Int, Int>>()

    for (line in lines) {
        val split = line.split(" -> ")
        for (i in (0 until split.size-1)) {
            val start = split[i].split(",").map{it.toInt() }
            val end = split[i+1].split(",").map{it.toInt() }

            for (col in ( min(start[0], end[0])..max(start[0], end[0]))) {
                for (row in ( min(start[1], end[1])..max(start[1], end[1]))) {
                    rocks.add(Pair(row, col))
                }
            }

        }
    }

    val maxDepth = rocks.maxOf { it.first }

    var sandAdded = 0
    var abyssed = false
    while(!abyssed) {
        sandAdded += 1
        var moved = true
        var sandLoc = Pair(0, 500)
        while(moved) {
            moved = false
            if (Pair(sandLoc.first+1, sandLoc.second) !in rocks) {
                sandLoc = Pair(sandLoc.first+1, sandLoc.second)
                moved = true
            } else if (Pair(sandLoc.first+1, sandLoc.second-1) !in rocks) {
                sandLoc = Pair(sandLoc.first+1, sandLoc.second-1)
                moved = true
            } else if (Pair(sandLoc.first+1, sandLoc.second+1) !in rocks) {
                sandLoc = Pair(sandLoc.first+1, sandLoc.second+1)
                moved = true
            }
            if (sandLoc.first > maxDepth) {
                moved = false
                abyssed = true
                sandAdded -= 1
            }
        }
        rocks.add(sandLoc)
    }

    println(sandAdded)


}