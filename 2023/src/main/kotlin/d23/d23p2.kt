package d23

import utils.Grids
import utils.Grids.GridSearch
import utils.Point
import utils.readCharGrid
import java.util.*

fun dp(
        grid: List<List<Char>>,
        visited: Set<Point>,
        start: Point,
        target: Point
): Int {

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
            return -Int.MAX_VALUE
        } else if (neighbors.size == 1) {
            toVisit.addAll(neighbors)
        } else {
            return visitedL.size + neighbors.map{ dp(grid, visited + visitedL, it, target) }.max()!!
        }
    }

    throw Error("Should never get here")
}



fun <T> aStar2(
        grid: List<List<T>>,
        start: Point,
        target: Point,
): GridSearch {

    val best = mutableMapOf<Point, GridSearch>()
    //val pq = PriorityQueue<Point>{ a, b -> - (best[a]!!.cost).compareTo(best[b]!!.cost) }
    val pq = mutableListOf<Point>()
    pq.add(start)
    best[start] = GridSearch(listOf(), 0)

    while(pq.isNotEmpty()) {
        val cur = pq.first()
        pq.remove(cur)

        val curV = grid[cur.row][cur.col]
        val neighbors = /*if (curV == '>') {
            setOf(Point(cur.row, cur.col+1))
        } else if (curV == '<') {
            setOf(Point(cur.row, cur.col-1))
        } else if (curV == 'v') {
            setOf(Point(cur.row+1, cur.col))
        }else if (curV == '^') {
            setOf(Point(cur.row-1, cur.col))
        } else {*/
            Grids.neighbors(cur, grid)
        //}

        val curBest = best[cur]

        val filteredNeighbors = neighbors
                .filter { grid[it.row][it.col] != '#' }
                .filter{ curBest == null || it !in curBest.path }
/*                .filter {
                    val nextVal = grid[it.row][it.col]
                    nextVal == '.' ||
                            (nextVal == '>' && it.col >= cur.col) ||
                            (nextVal == '<' && it.col <= cur.col) ||
                            (nextVal == '^' && it.row <= cur.row) ||
                            (nextVal == 'v' && it.row >= cur.row)
                }*/

        for (n in filteredNeighbors) {
            val nCost = best[cur]!!.cost + 1

            if (n !in best || nCost > best[n]!!.cost) {
                best[n] = GridSearch(best[cur]!!.path + cur, nCost)
                pq.add(n)
            }
        }
    }

    return best[target]!!
}


fun main() {
    val grid = readCharGrid("/d23.txt")

    println(dp(grid, setOf(), Point(0, 1), Point(grid.size-1, grid[0].size-2))-1)

/*    val bestPath = aStar2(
            grid,
            Point(0, 1),
            Point(grid.size-1, grid[0].size-2)
    )
    println(bestPath.cost)
    println(bestPath.path.size)

    for (row in grid.indices) {
        for (col in grid[0].indices) {
            if ( Point(row, col) in bestPath.path) print('O') else print(grid[row][col])
        }
        println()
    }*/




}