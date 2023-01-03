package d12

import utils.readCharGrid
import utils.readList


fun findSteps(start: Pair<Int, Int>, goal: Pair<Int, Int>, grid: List<List<Char>>): Int {
    var visited = mutableMapOf<Pair<Int, Int>, Int>()
    var toVisit = mutableMapOf<Pair<Int, Int>, Int>()
    toVisit[start] = 0

    do {
        val visiting = toVisit.keys.first()
        val steps = toVisit[visiting]!!
        toVisit.remove(visiting)
        visited[visiting] = steps

        if (visiting == goal) {
            return steps
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
    return Int.MAX_VALUE
}

fun main() {
    val grid = readCharGrid("/d12.txt")


    val goal = Pair(20, 68)
    val steps = mutableSetOf<Int>()

    for (row in grid.indices) {
        for (col in grid[0].indices) {
            if (grid[row][col] == 'a') {
                steps.add(findSteps(Pair(row, col), goal, grid))
            }
        }
    }

    println(steps.min())


}
