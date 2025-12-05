package d4

import utils.Grids
import utils.Point
import utils.readCharGrid

fun main() {
    val grid = readCharGrid("/d4.txt")

    var reachable = 0
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            val neighbors = Grids.diagNeighbors(Point(row, col), grid)
            val filledNeighbors = neighbors.count{ grid[it.row][it.col] == '@'}
            if (filledNeighbors < 4 && grid[row][col] == '@') {
                reachable++
            }
        }
    }

    println(reachable)

}