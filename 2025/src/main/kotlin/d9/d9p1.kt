package d9

import utils.Point
import utils.readList

fun main() {
    val lines = readList("/d9.txt")
    val tiles = lines.map{
        val s = it.split(",")
        Point(s[0].toInt(), s[1].toInt())
    }

    var max = 0L
    for (tile1 in tiles) {
        for (tile2 in tiles) {
            if (tile1 != tile2) {
                val dx = Math.abs(tile1.col - tile2.col).toLong() + 1
                val dy = Math.abs(tile1.row - tile2.row) + 1
                if (dx*dy > max) max = dx*dy
            }
        }
    }
    println(max)



}