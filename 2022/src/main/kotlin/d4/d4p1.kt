package d4

import utils.*

fun toRange(l: String): LongRange {
    val split = l.split("-")
    return (split[0].toLong()..split[1].toLong())
}

fun main() {
    val lines = readList("/d4.txt")

    println(
            lines.map { it.split(",") }
                    .map { Pair(toRange(it[0]), toRange(it[1])) }
                    .count { it.first.intersect(it.second) == it.first.toSet() || it.first.intersect(it.second) == it.second.toSet() }
    )
}