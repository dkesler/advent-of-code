package d4

import utils.Grids
import utils.Point
import utils.readCharGrid

fun main() {
    val grid = readCharGrid("/d4.txt")

    val removed = mutableSetOf<Point>()

    var added = true
    while (added) {
        added = false
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                val neighbors = Grids.diagNeighbors(Point(row, col), grid)
                val filledNeighbors = neighbors.count{ grid[it.row][it.col] == '@' && Point(it.row, it.col) !in removed}
                if (filledNeighbors < 4 && grid[row][col] == '@' && Point(row, col) !in removed) {
                    removed += Point(row, col)
                    added = true
                }
            }
        }
    }

    println(removed.size)

}