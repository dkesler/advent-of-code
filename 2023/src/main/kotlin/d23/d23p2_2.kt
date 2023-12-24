package d23

import utils.Grids
import utils.Grids.GridSearch
import utils.Point
import utils.readCharGrid
import java.util.*

val memoTable = mutableMapOf< Pair<Point, Set<Point>>, Int>()

fun dpmemo(
        grid: List<List<Char>>,
        visited: Set<Point>,
        start: Point,
        target: Point
): Int {

    val reachableSet = Grids.floodfill(grid, start, {it -> Point(it.row, it.col) !in visited && it.value != '#'})
    if (target !in reachableSet) return Int.MIN_VALUE

    val memoKey = Pair(start, reachableSet)
    if (memoKey in memoTable.keys) {
        return memoTable[memoKey]!!
    }

    val toVisit = mutableSetOf(start)
    val visitedL = mutableSetOf<Point>()
    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)
        visitedL.add(visiting)

        if (visiting == target) return visitedL.size

        val neighbors = Grids.neighbors(visiting, grid)
                .filter{ grid[it.row][it.col] != '#' }
                .filter{ it !in visited && it !in visitedL }

        if (neighbors.size == 0) {
            //return -Int.MAX_VALUE
            throw Error("hit deadend but that should never happen due to reachable set check")
        } else if (neighbors.size == 1) {
            toVisit.addAll(neighbors)
        } else {
            val i = visitedL.size + neighbors.map { dpmemo(grid, visited + visitedL, it, target) }.max()!!
            memoTable[memoKey] = i
            return i
        }
    }

    throw Error("Should never get here")
}

fun main() {
    val grid = readCharGrid("/d23.txt")

    println(dpmemo(grid, setOf(), Point(0, 1), Point(grid.size-1, grid[0].size-2))-1)
}