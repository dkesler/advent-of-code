package d20

import java.lang.RuntimeException

fun main() {

    val aa = Node("AA")
    val bc1 = Node("BC1")
    val bc2 = Node("BC2")
    val de1 = Node("DE1")
    val de2 = Node("DE2")
    val fg1 = Node("FG1")
    val fg2 = Node("FG2")
    val zz = Node("ZZ")

    aa.neighbors = listOf(
        Edge("ZZ", 26),
        Edge("BC1", 4),
        Edge("FG2", 30)
    )

    bc1.neighbors = listOf(
        Edge("ZZ", 28),
        Edge("AA", 4),
        Edge("FG2",32),
        Edge("BC2", 1)
    )

    zz.neighbors = listOf(
        Edge("AA", 26),
        Edge("BC1", 28),
        Edge("FG2", 6)
    )

    fg2.neighbors = listOf(
        Edge("AA", 30),
        Edge("BC1", 32),
        Edge("ZZ", 6),
        Edge("FG1", 1)

    )

    bc2.neighbors = listOf(
        Edge("DE1", 6),
        Edge("BC1", 1)
    )

    de1.neighbors = listOf(
        Edge("BC2", 6),
        Edge("DE2", 1)
    )

    de2.neighbors = listOf(
        Edge("FG1", 4),
        Edge("DE1", 1)
    )

    fg1.neighbors = listOf(
        Edge("DE2", 4),
        Edge("FG2", 1)
    )

    val nodeMap = listOf(aa, zz, bc1, bc2, de1, de2, fg1, fg2).map{ Pair(it.name, it) }.toMap()

    val distance = findPath(aa, zz, nodeMap, setOf("AA"), 0, Int.MAX_VALUE)

    println(distance)

    if (distance.second != 23) {
        throw RuntimeException(distance.second.toString())
    }


}
