package d13

import kotlin.math.min

fun toSoonestArrival(minTimestamp: Long, busId: Long): Long  {
    return Math.ceil(minTimestamp.toDouble() / busId.toDouble()).toLong() * busId
}

fun main() {
    val content = Class::class.java.getResource("/d13/d13p1").readText()
    val list = content.split("\n").filter{it != ""}

    val minTimestamp = list[0].toLong()
    val busIds = list[1].split(",").filter{it != "x"}.map(String::toLong)

    val soonestArrival = busIds.map{ Pair(it, toSoonestArrival(minTimestamp, it)) }

    val s = soonestArrival.sortedBy { it.second }.get(0)
    println(soonestArrival)
    println(s)
    println(s.first * (s.second - minTimestamp))




}
