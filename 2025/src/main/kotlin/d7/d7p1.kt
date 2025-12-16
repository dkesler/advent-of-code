package d7

import utils.Grids
import utils.Point
import utils.readCharGrid

fun main() {
    val grid = readCharGrid("/d7test.txt")

    val start = grid[0].indexOf('S')

    val fill = Grids.floodfill(
        grid,
        Point(0, start),
        {
            visiting, neighbor ->
            if (visiting.value != '^' && neighbor.col == visiting.col && neighbor.row > visiting.row) true
            else if (visiting.value == '^' && neighbor.row == visiting.row) true
            else false
        }
    )

    var count = 0
    for (row in grid.indices) {
        for (col in grid[0].indices) {
            if (grid[row][col] == '^' && Point(row, col) in fill) count++
        }
    }
    println(count)

    var timelineCount = 0L
    for (col in grid[0].indices) {
        val point = Point(grid.size - 1, col)
        timelineCount += timelinesReaching(point, grid)
    }

    println(timelineCount)

    printGrid(grid, fill)


}

val memoTable = mutableMapOf<Point, Long>()
fun timelinesReaching(point: Point, grid: List<List<Char>>): Long {
    if (point in memoTable) return memoTable[point]!!

    if (grid[point.row][point.col] == 'S') return 1

    var tls = 0L
    if (point.col+1 < grid[0].size && grid[point.row][point.col+1] == '^') tls += timelinesReaching(Point(point.row, point.col+1), grid)
    if (point.col-1 >= 0 && grid[point.row][point.col-1] == '^') tls += timelinesReaching(Point(point.row, point.col-1), grid)
    if (point.row > 0 && grid[point.row-1][point.col] != '^') tls += timelinesReaching(Point(point.row-1, point.col), grid)
    memoTable[point] = tls
    return tls
}

private fun printGrid(
    grid: List<List<Char>>,
    fill: Set<Point>
) {
    for (row in grid.indices) {
        for (col in grid[0].indices) {
            val pt = Point(row, col)
            if (grid[row][col] == '^') print('^')
            else if (pt in fill) print('|')
            else print(grid[row][col])
        }
        println()
    }
}