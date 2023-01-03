package d4

import utils.*

fun main() {
    val lines = readList("/d4.txt")

    println(
            lines.map { it.split(",") }
                    .map { Pair(toRange(it[0]), toRange(it[1])) }
                    .count { it.first.intersect(it.second).isNotEmpty() }
    )
}