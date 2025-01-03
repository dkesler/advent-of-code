package d16

import utils.*
import java.util.*

fun <T> aStar2(
    starts: Set<T>,
    isTarget: (T) -> Boolean,
    neighbors: (T) -> Set<T>,
    cost: (T, T) -> Long,
    heuristic: (T) -> Long
): MutableMap<T, Grids.GraphSearch<T>> {

    val best = mutableMapOf<T, Grids.GraphSearch<T>>()
    val pq = PriorityQueue<T>{ a, b -> (best[a]!!.cost + heuristic(a)).compareTo(best[b]!!.cost + heuristic(b)) }
    starts.forEach{ best[it] = Grids.GraphSearch(listOf(), 0) }
    pq.addAll(starts)

    while(pq.isNotEmpty()) {
        val current = pq.poll()
        //if (isTarget(current)) return best[current]!!

        val ns = neighbors(current)
        for (n in ns) {
            val nCost = best[current]!!.cost + cost(current, n)
            if (n !in best || nCost < best[n]!!.cost) {
                best[n] = Grids.GraphSearch(best[current]!!.path + current, nCost)
                pq.add(n)
            }
        }
    }

    return best
}

fun main() {
    val grid = readCharGrid("/d16.txt")

    val start = Grids.pointValues(grid).filter{ it.value == 'S' }.first().toPoint()
    val end  = Grids.pointValues(grid).filter{ it.value == 'E' }.first().toPoint()

/*    val target = 122492L
    var onBest = 0
    for (point in Grids.pointValues(grid).filter{ it.value != '#' }) {
        for (dir in setOf("N", "S", "E", "W")) {
            val intermediate = Pair(point.toPoint(), dir)
            val prev = getPrev(intermediate)
            if (grid[prev.first.row][prev.first.col] != '#') {
                val bestTo = findBest(Pair(start, "E"), intermediate, grid)
                val bestFrom = findBest(intermediate, Pair(end, "E"), grid)
                if (bestFrom.cost + bestTo.cost == target) onBest++
            }
        }
    }*/

    val neighbors = { cur: Pair<Point, String> ->
        val next = getNext(cur)
        val rotated = getRotations(cur)
        if (grid[next.first.row][next.first.col] != '#')
            rotated + next
        else
            rotated
    }
    val cost: (Pair<Point, String>, Pair<Point, String>) -> Long = { cur: Pair<Point, String>, next: Pair<Point, String> -> if (cur.second == next.second) 1 else 1000 }

    val bestMap = aStar2(
        setOf(Pair(start, "E")),
        { it == Pair(end, "E") },
        neighbors,
        cost,
        { 0 }
    )

    var bestSet = bestMap[Pair(end, "E")]!!.path.toMutableSet()
    var added = true
    while(added) {
        added = false
        for (point in bestMap.keys.filter{ it !in bestSet }) {
            val ns = neighbors(point).filter { it in bestSet }
            if (ns.any { n -> bestMap[n]!!.cost == bestMap[point]!!.cost + cost(point, n) }) {
                bestSet.add(point)
                bestSet.addAll(bestMap[point]!!.path.filter{ it !in bestSet })
                added = true
            }
        }
    }

    println(bestSet.map{it.first}.toSet().size)

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (Point(row, col) in bestSet.map{ it.first } ) {
                print("O")
            } else {
                print(grid[row][col])
            }
        }
        println("")
    }

}
//wrong: 519
