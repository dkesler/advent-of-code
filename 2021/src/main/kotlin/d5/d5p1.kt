package d5

import utils.*

fun main() {
    val list = readList("/d5/d5p1").map{
        val endpoints = it.split("->")
        val st = endpoints[0].split(",").map { it.trim().toInt() }
        val end = endpoints[1].split(",").map { it.trim().toInt() }
        Line(st[0], st[1], end[0], end[1])
    }

    val covered = mutableMapOf<Pair<Int, Int>, Int>()

    list.forEach{ addPoint(covered, it.points()) }

    println(covered.filter{it.value > 1}.size)


}

fun addPoint(covered: MutableMap<Pair<Int, Int>, Int>, points: List<Pair<Int, Int>>) {
    points.forEach{
        covered.put(it, covered.getOrDefault(it, 0)+1)
    }
}

class Line(val x1: Int, val y1: Int, val x2: Int, val y2:Int) {
    fun points(): List<Pair<Int, Int>> {
        if (x1 == x2) {
            val range = getRange(y1, y2)
            return range.map{ Pair(x1, it) }
        } else if (y1 == y2) {
            return getRange(x1, x2).map{ Pair(it, y1) }
        } else {
            val xRange = if (x1 > x2) x1.downTo(x2) else x1.rangeTo(x2)
            val yRange = if (y1 > y2) y1.downTo(y2) else y1.rangeTo(y2)
            return xRange.zip(yRange)
        }
    }
}

fun getRange( a: Int,  b: Int): IntRange {
    if (a > b) {
        val intRange = b..a
        return intRange
    } else {
        return a..b
    }

}
