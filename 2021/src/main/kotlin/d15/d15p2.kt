package d15

import utils.*

fun main() {
    val g =  readLongGrid("/d15/d15p1")

    val expandedMap = expand(g)

    val start = System.currentTimeMillis()
    val lowestRiskPath = findLowestRiskPath2(expandedMap, Pair(0, 0), Pair(expandedMap.size-1, expandedMap.size-1))
    println("Took ${System.currentTimeMillis() - start} ms")

    println(lowestRiskPath.risk)

}

fun expand(g: List<List<Long>>): List<List<Long>> {
    return (0 until g.size*5).map{ x ->
        (0 until g.size*5).map { y ->
            val baseRisk = g[x%g.size][y%g.size]
            val adjustedRisk = (baseRisk + x/g.size + y/g.size - 1) % 9 + 1
            adjustedRisk
        }
    }
}
