package d15

import utils.*
import java.util.*

fun main() {
    val g =  readLongGrid("/d15/d15p1")

    val start = System.currentTimeMillis()
    val lowestRiskPath = findLowestRiskPath2(g, Pair(0, 0), Pair(g.size-1, g.size-1))
    println("Took ${System.currentTimeMillis() - start} ms")

    println(lowestRiskPath.risk)

}

data class Path(val path: List<Pair<Int, Int>>, val risk: Long)

//My original weird hacky algorithm that i used to actually solve the problem
fun findLowestRiskPath2(g: List<List<Long>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Path {
    fun path(current: Pair<Int, Int>, bestPossiblePaths: MutableMap<Pair<Int, Int>, Path>): Boolean {
        if (current == end) {
            val p = Path(listOf(), 0)
            bestPossiblePaths[current] = p
            return false
        } else {
            val adj = listOf(
                Pair(current.first+1, current.second),
                Pair(current.first, current.second+1),
                Pair(current.first-1, current.second),
                Pair(current.first, current.second-1)
            ).filter{it.first >= 0 && it.first < g.size && it.second >= 0 &&  it.second < g.size && bestPossiblePaths.containsKey(it)}

            val bestNode = adj.sortedBy { g[it.first][it.second] + bestPossiblePaths[it]!!.risk }.first()
            val bestPath = bestPossiblePaths[bestNode]!!

            val newEntry = Path(listOf(bestNode).plus(bestPath.path), g[bestNode.first][bestNode.second] + bestPath.risk)
            val improved = !bestPossiblePaths.containsKey(current) || bestPossiblePaths[current]!!.risk > newEntry.risk
            if (improved) {
                bestPossiblePaths.put(
                    current,
                    newEntry
                )
            }
            return improved
        }
    }

    val bestPossiblePaths = mutableMapOf<Pair<Int, Int>, Path>()
    var starts = g.indices.map{ x -> g.indices.map{ Pair(x, it) }}.flatten().sortedBy { it.first * g.size + it.second }.reversed()
    starts.forEach{ path(it, bestPossiblePaths) }

    while(starts.isNotEmpty()) {
        starts = starts.filter{path(it, bestPossiblePaths)}.flatMap{
            listOf(
                Pair(it.first+1, it.second),
                Pair(it.first, it.second+1),
                Pair(it.first-1, it.second),
                Pair(it.first, it.second-1)
            ).filter{it.first >= 0 && it.first < g.size && it.second >= 0 && it.second < g.size}
        }.toSet().toList()
        println("iterating")
    }
    return bestPossiblePaths[start]!!
}

//Wrote this afterwards to try to remember the correct algorithm and compare my approach
fun findLowestRiskPathDijkstra(g: List<List<Long>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Path {
    val tenativeDistances = mutableMapOf<Pair<Int, Int>, Path>(
        Pair(start, Path(listOf(start), 0))
    )
    val visited = mutableSetOf<Pair<Int, Int>>()
    val toVisit = PriorityQueue<Pair<Int, Int>>(kotlin.Comparator { o1, o2 ->
        (tenativeDistances[o1]!!.risk - tenativeDistances[o2]!!.risk).toInt()
    })
    toVisit.add(start)

    while(toVisit.isNotEmpty() && !visited.contains(end)) {
        val current = toVisit.first()
        toVisit.remove(current)
        visited.add(current)
        val adjacencies = listOf(
            Pair(current.first+1, current.second),
            Pair(current.first, current.second+1),
            Pair(current.first-1, current.second),
            Pair(current.first, current.second-1)
        ).filter{it.first >= 0 && it.first < g.size && it.second >= 0 &&  it.second < g.size && !visited.contains(it)}

        adjacencies.forEach {
            val newTenativeDistance = tenativeDistances[current]!!.risk + g[it.first][it.second]
            val oldTenatativeDistance = tenativeDistances[it]?.risk ?: Long.MAX_VALUE
            if (newTenativeDistance < oldTenatativeDistance) {
                tenativeDistances[it] = Path(tenativeDistances[current]!!.path + it, newTenativeDistance)
            }
            if (!toVisit.contains(it)) {
                toVisit.add(it)
            }
        }
    }

    return tenativeDistances[end]!!
}

