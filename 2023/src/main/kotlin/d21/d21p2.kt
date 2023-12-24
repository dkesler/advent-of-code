package d21

import utils.*

fun main() {
    val grid = readCharGrid("/d21test.txt")

    /*
     odd grid count * num full odd grids
     + even grid count * num full even grids
     + ensw grids
     + 202300 ne grids
     + 202300 nw grids
     + 202300 se grids
     + 202300 sw grids
     */

    val startV = Grids.pointValues(grid).filter{ it.value == 'S' }.first()
    val start = Point(startV.row, startV.col)
    val steps = 26501365
    //val steps = 50

    val toVisit = mutableSetOf(Pair(0, start))
    val visited = mutableSetOf<Point>()
    val reachable = mutableSetOf<Point>()
    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        //visited.add(visiting.second)
        toVisit.remove(visiting)

        if ((visiting.first % 2) == 0) reachable.add(visiting.second)

        if (visiting.first < steps) {
            val neighbors = setOf(
                    Point(visiting.second.row, visiting.second.col + 1),
                    Point(visiting.second.row, visiting.second.col - 1),
                    Point(visiting.second.row + 1, visiting.second.col),
                    Point(visiting.second.row - 1, visiting.second.col)
            )
            val n = neighbors
                    .filter { it !in visited }
                    .filter {
                        val r = if (it.row % grid.size >= 0) it.row % grid.size else it.row % grid.size + grid.size
                        val c = if (it.col % grid[0].size >= 0) it.col % grid[0].size else it.col % grid[0].size + grid[0].size
                        grid[r][c] != '#'
                    }
                    .map { Pair(visiting.first + 1, it) }
            toVisit.addAll(n)
            visited.addAll(n.map{ it.second})
        }
    }

    println(
            reachable.size
    )






}