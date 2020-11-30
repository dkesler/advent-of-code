package d20

import java.lang.RuntimeException

fun main() {

    val aa = Node("AA", 0)
    val bc1 = Node("BC1", 0)
    val bc2 = Node("BC2", 0)
    val de1 = Node("DE1", 0)
    val de2 = Node("DE2", 0)
    val fg1 = Node("FG1", 0)
    val fg2 = Node("FG2", 0)
    val zz = Node("ZZ", 0)

    aa.neighbors = listOf(
        Edge("ZZ", 26, 0),
        Edge("BC1", 4, 0),
        Edge("FG2", 30, 0)
    )

    bc1.neighbors = listOf(
        Edge("ZZ", 28, 0),
        Edge("AA", 4, 0),
        Edge("FG2",32, 0),
        Edge("BC2", 1, 0)
    )

    zz.neighbors = listOf(
        Edge("AA", 26, 0),
        Edge("BC1", 28, 0),
        Edge("FG2", 6, 0)
    )

    fg2.neighbors = listOf(
        Edge("AA", 30, 0),
        Edge("BC1", 32, 0),
        Edge("ZZ", 6, 0),
        Edge("FG1", 1, 0)

    )

    bc2.neighbors = listOf(
        Edge("DE1", 6, 0),
        Edge("BC1", 1, 0)
    )

    de1.neighbors = listOf(
        Edge("BC2", 6, 0),
        Edge("DE2", 1, 0)
    )

    de2.neighbors = listOf(
        Edge("FG1", 4, 0),
        Edge("DE1", 1, 0)
    )

    fg1.neighbors = listOf(
        Edge("DE2", 4, 0),
        Edge("FG2", 1, 0)
    )

    val nodeMap = listOf(aa, zz, bc1, bc2, de1, de2, fg1, fg2).map{ Pair(it.name, it) }.toMap()

    val distance = Solver().findPath(aa, zz, nodeMap, setOf(Pair("AA", 0)), 0,0, Int.MAX_VALUE, 0)

    println(distance)

    if (distance.second != 23) {
        throw RuntimeException(distance.second.toString())
    }


}
