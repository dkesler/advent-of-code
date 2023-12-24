package d21

import utils.*
import utils.Grids.floodfill
import utils.Grids.points

fun main() {
    val grid = readCharGrid("/d21.txt")

    val startV = Grids.pointValues(grid).filter{ it.value == 'S' }.first()
    val start = Point(startV.row, startV.col)

    val toVisit = mutableSetOf(Pair(0, start))
    val visited = mutableSetOf<Pair<Int, Point>>()
    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        visited.add(visiting)
        toVisit.remove(visiting)

        if (visiting.first < 64) {
            toVisit.addAll(
                    Grids.neighbors(visiting.second, grid)
                            .filter { Pair(visiting.first+1, it) !in visited }
                            .filter{ grid[it.row][it.col] != '#' }
                            .map{ Pair(visiting.first+1, it)}
            )
        }
    }

    println(visited.filter{ it.first == 64 }.size)






}