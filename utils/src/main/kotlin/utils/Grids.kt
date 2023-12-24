package utils

import java.util.*
import kotlin.math.abs

open class PointBase(open val row: Int, open val col: Int) {
    fun manhattanDistance(other: PointBase): Int {
        return abs(other.row - row) + abs(other.col - col)
    }
}

data class Point(override val row: Int, override val col: Int): PointBase(row, col)
data class PointValue<T>(override val row: Int, override val col: Int, val value: T): PointBase(row, col) {
    fun toPoint(): Point {
        return Point(row, col)
    }
}

object Grids {


    fun <T> transpose(grid: List<List<T>>): List<List<T>> {
        return grid[0].indices.map { col ->
            grid.indices.map { row ->
                grid[row][col]
            }
        }
    }

    fun <T> rot90CW(grid: List<List<T>>): List<List<T>> {
        return grid[0].indices.map { col ->
            grid.indices.reversed().map { row ->
                grid[row][col]
            }
        }
    }

    fun <T> rot90CCW(grid: List<List<T>>): List<List<T>> {
        return grid[0].indices.reversed().map { col ->
            grid.indices.map { row ->
                grid[row][col]
            }
        }
    }

    fun <T> rot180(grid: List<List<T>>): List<List<T>> {
        return grid.indices.reversed().map { row ->
            grid[0].indices.reversed().map { col ->
                grid[row][col]
            }
        }
    }

    fun <T> flipH(grid: List<List<T>>): List<List<T>> {
        return grid.indices.map { row ->
            grid[0].indices.reversed().map { col ->
                grid[row][col]
            }
        }
    }

    fun <T> flipV(grid: List<List<T>>): List<List<T>> {
        return grid.indices.reversed().map { row ->
            grid[0].indices.map { col ->
                grid[row][col]
            }
        }
    }

    fun <T> points(grid: List<List<T>>): List<Point> {
        return grid.indices.flatMap { row ->
            grid[row].indices.map { col ->
                Point(row, col)
            }
        }
    }

    fun <T> pointValues(grid: List<List<T>>): List<PointValue<T>> {
        return grid.indices.flatMap { row ->
            grid[row].indices.map { col ->
                PointValue(row, col, grid[row][col])
            }
        }
    }

    fun <T> toMutable(grid: List<List<T>>): MutableList<MutableList<T>> {
        return grid.map{ it.toMutableList() }.toMutableList()
    }

    fun <T> floodfill(grid: List<List<T>>, start: Point, floodTo: (PointValue<T>) -> Boolean ): Set<Point> {
        return floodfill(grid, start) { _, next -> floodTo(next) }
    }
    fun <T> floodfill(grid: List<List<T>>, start: Point, floodTo: (PointValue<T>, PointValue<T>) -> Boolean ): Set<Point> {
        val toVisit = mutableSetOf(start)
        val visited = mutableSetOf<Point>()
        while(toVisit.isNotEmpty()) {
            val visiting = toVisit.first()
            visited.add(visiting)
            toVisit.remove(visiting)

            toVisit.addAll(
                neighbors(visiting, grid)
                        .filter { it !in visited }
                        .filter { floodTo(
                                PointValue(visiting.row, visiting.col, grid[visiting.row][visiting.col]),
                                PointValue(it.row, it.col, grid[it.row][it.col])
                        )}
            )
        }
        return visited
    }

    fun <T> fullFloodFill(grid: List<List<T>>, floodTo: (PointValue<T>) -> Boolean, fillFrom: (PointValue<T>) -> Boolean = {_->true}): Set<Set<Point>> {
        return fullFloodFill(grid, { _, next -> floodTo(next) }, fillFrom)
    }

    fun <T> fullFloodFill(grid: List<List<T>>, floodTo: (PointValue<T>, PointValue<T>) -> Boolean, fillFrom: (PointValue<T>) -> Boolean = {_->true}): Set<Set<Point>> {
        val fills = mutableSetOf<Set<Point>>()
        val filled = mutableSetOf<Point>()

        for (point in points(grid)) {
            if (point !in filled && fillFrom(PointValue(point.row, point.col, grid[point.row][point.col]))) {
                val fill = floodfill(grid, point, floodTo)
                fills.add(fill)
                filled.addAll(fill)
            }
        }
        return fills
    }

    fun <T> neighbors(point: Point, grid: List<List<T>>): Set<Point> {
        return setOf(
                Point(point.row, point.col+1),
                Point(point.row, point.col-1),
                Point(point.row+1, point.col),
                Point(point.row-1, point.col)
        ).filter{ it.row in grid.indices && it.col in grid[it.row].indices}.toSet()
    }

    data class GridSearch(val path: List<Point>, val cost: Long)
    fun <T> aStar(
            grid: List<List<T>>,
            start: Point,
            target: Point,
            cost: (Point, Point) -> Long,
            heuristic: (Point) -> Long
    ): GridSearch {

        val best = mutableMapOf<Point, GridSearch>()
        val pq = PriorityQueue<Point>{ a, b -> (best[a]!!.cost + heuristic(a)).compareTo(best[b]!!.cost + heuristic(b)) }
        pq.add(start)
        best[start] = GridSearch(listOf(), 0)

        while(pq.isNotEmpty()) {
            val current = pq.poll()
            if (current == target) return best[target]!!

            val neighbors = neighbors(current, grid)
            for (n in neighbors) {
                val nCost = best[current]!!.cost + cost(current, n)
                if (n !in best || nCost < best[n]!!.cost) {
                    best[n] = GridSearch(best[current]!!.path + current, nCost)
                    pq.add(n)
                }
            }
        }

        throw Error("Could not finish search")
    }

    data class GraphSearch<T>(val path: List<T>, val cost: Long)
    fun <T> aStar(
            starts: Set<T>,
            isTarget: (T) -> Boolean,
            neighbors: (T) -> Set<T>,
            cost: (T, T) -> Long,
            heuristic: (T) -> Long
    ): GraphSearch<T> {

        val best = mutableMapOf<T, GraphSearch<T>>()
        val pq = PriorityQueue<T>{ a, b -> (best[a]!!.cost + heuristic(a)).compareTo(best[b]!!.cost + heuristic(b)) }
        starts.forEach{ best[it] = GraphSearch(listOf(), 0) }
        pq.addAll(starts)

        while(pq.isNotEmpty()) {
            val current = pq.poll()
            if (isTarget(current)) return best[current]!!

            val ns = neighbors(current)
            for (n in ns) {
                val nCost = best[current]!!.cost + cost(current, n)
                if (n !in best || nCost < best[n]!!.cost) {
                    best[n] = GraphSearch(best[current]!!.path + current, nCost)
                    pq.add(n)
                }
            }
        }

        throw Error("Could not finish search")
    }


    //floodfills/a* on sparse grid and/or graph?
}