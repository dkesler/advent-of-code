package d9

import utils.Point
import utils.readList
import kotlin.math.max
import kotlin.math.min

//this algorithm is slow and misses some edge cases but still somehow managed to get the right answer
fun main() {
    val lines = readList("/d9.txt")
    val tiles = lines.map{
        val s = it.split(",")
        Point(s[0].toInt(), s[1].toInt())
    }

    val border = mutableSetOf<Point>()
    for (tileIdx in tiles.indices) {
        val cur = tiles[tileIdx]
        border.add(cur)
        val nextIdx = if (tileIdx == tiles.size-1) 0 else tileIdx+1
        val next = tiles[nextIdx]
        if (cur.row == next.row) {
            border.addAll(
                (min(cur.col, next.col)..max(cur.col, next.col)).map { Point(cur.row, it) }
            )
        } else {
            border.addAll(
                (min(cur.row, next.row)..max(cur.row, next.row)).map { Point(it, cur.col) }
            )
        }
    }




    var max = 0L
    println("Total checks: ${tiles.size*tiles.size}")
    var checks = 0
    for (tile1 in tiles) {
        for (tile2 in tiles) {
            checks++
            if (checks % 1000 == 0) println("Check: $checks, current max: $max")
            if (tile1 != tile2) {
                val dx = Math.abs(tile1.col - tile2.col).toLong() + 1
                val dy = Math.abs(tile1.row - tile2.row) + 1
                if (dx*dy > max && isLegal(tile1, tile2, border)) max = dx*dy
            }
        }
    }
    println(max)




}

fun isLegal(tile1: Point, tile2: Point, border: MutableSet<Point>): Boolean {
    val minRow = min(tile1.row, tile2.row)
    val maxRow = max(tile1.row, tile2.row)
    val minCol = min(tile1.col, tile2.col)
    val maxCol = max(tile1.col, tile2.col)

    fun within(pt: Point): Boolean {
        return pt.row in minRow..maxRow && pt.col >= minCol && pt.col <= maxCol
    }
    for (point in border) {
        val uNeighbor = Point(point.row-1, point.col)
        val dNeighbor = Point(point.row+1, point.col)
        val eNeighbor = Point(point.row, point.col+1)
        val wNeighbor = Point(point.row, point.col-1)

        if (uNeighbor !in border && dNeighbor !in border && within(uNeighbor) && within(dNeighbor)) {
            return false
        }

        if (eNeighbor !in border && wNeighbor !in border && within(eNeighbor) && within(wNeighbor)) {
            return false
        }
    }
    return true
}
