package d12

import utils.readCharGrid

fun main() {
    val grid = readCharGrid("/d12.txt")

    val goal = Pair(20, 68)

    var visited = mutableMapOf<Pair<Int, Int>, Int>()
    var toVisit = mutableMapOf<Pair<Int, Int>, Int>()
    toVisit[Pair(20, 0)] = 0

    do {
        val visiting = toVisit.keys.first()
        val steps = toVisit[visiting]!!
        toVisit.remove(visiting)

        if (visiting == goal) {
            println(steps)
            break
        }

        val next = listOf(
                Pair(visiting.first-1, visiting.second),
                Pair(visiting.first+1, visiting.second),
                Pair(visiting.first, visiting.second-1),
                Pair(visiting.first, visiting.second+1)
        ).filter{ it.first in grid.indices && it.second in grid[0].indices }
                .filter{ grid[it.first][it.second].toInt() <= grid[visiting.first][visiting.second].toInt()+1 }
                .filter{ it !in visited.keys && it !in toVisit.keys}

        next.forEach{ toVisit[it] = (steps + 1) }
    } while(!toVisit.isEmpty())



}
