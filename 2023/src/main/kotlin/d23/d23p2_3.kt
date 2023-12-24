package d23

import d17.neigh
import utils.Grids
import utils.Point
import utils.readCharGrid

data class Segment(val endpoints: Set<Point>, val size: Int)
fun findSegment(
        grid: List<List<Char>>,
        knownSegmentEndpoints: Set<Point>,
        start: Point
): Segment {

    val toVisit = mutableSetOf(start)
    val visitedL = mutableSetOf<Point>()
    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)
        visitedL.add(visiting)

        val neighbors = Grids.neighbors(visiting, grid)
                .filter{ grid[it.row][it.col] != '#' }
                .filter{ it !in visitedL }
                .filter{ it !in knownSegmentEndpoints }

        if (neighbors.size == 0) {
            return Segment(setOf(start, visiting), visitedL.size)
        } else if (neighbors.size == 1) {
            toVisit.addAll(neighbors)
        } else {
            return Segment(setOf(start, visiting), visitedL.size)
        }
    }

    throw Error("Should never get here")
}

fun dfs(start: Point, end: Point, visited: Set<Point>, adjacency: Map<Point, Set<Pair<Point, Segment>>>): Int {
    if (start == end) return 0

    val neighbors = adjacency[start]!!.filter{ it.first !in visited }
    if (neighbors.size == 0) return Int.MIN_VALUE

    return neighbors.map{ dfs(it.first, end, visited + start, adjacency) + it.second.size }.max()!!
}


fun main() {
    val grid = readCharGrid("/d23.txt")

    val islands = Grids.pointValues(grid)
            .filter { it.value != '#' }
            .filter{ Grids.neighbors(it.toPoint(), grid).filter{ grid[it.row][it.col] != '#' }.count() > 2 }
            .map{it.toPoint() }
            .toMutableSet()
    val start = Point(0, 1)
    islands.add(start)
    val target = Point(grid.size - 1, grid[0].size - 2)
    islands.add(target)

    val segments = mutableSetOf<Segment>()
    for (island in islands) {
        val neighbors = Grids.neighbors(island, grid).filter{ grid[it.row][it.col] != '#'}
        neighbors.map{
            val rawSeg = findSegment(grid, setOf(island), it)
            Segment(rawSeg.endpoints - it + island, rawSeg.size )
        }
                .forEach{ segments.add(it) }
    }

    val adjacency = mutableMapOf<Point, Set<Pair<Point, Segment>> >()
    for (island in islands) {
        adjacency[island] = segments.filter{ island in it.endpoints }.map {
            Pair((it.endpoints - island).first(), it)
        }.toSet()
    }

    println(dfs(start, target, setOf(start), adjacency))
}