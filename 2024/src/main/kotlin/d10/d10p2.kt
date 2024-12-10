package d10

import utils.*

fun main() {
    val grid = readLongGrid("/d10.txt")

    var trailHeadScores = 0L

    val floodFills = mutableListOf<Set<PointValue<Long>>>()

    for (possibleTrailHead in Grids.pointValues(grid).filter{it.value == 0L}) {
        val fill = Grids.floodfill(
            grid,
            possibleTrailHead.toPoint(),
            { from, to -> from.value == to.value - 1 },
        )

        floodFills.add( fill.map{ PointValue(it.row, it.col, grid[it.row][it.col])}.toSet() )
    }

    for (fill in floodFills) {
        val pathsToNine = mutableMapOf<Point, Long>()
        for (point in fill.sortedBy { grid[it.row][it.col] }.reversed()) {
            if (grid[point.row][point.col] == 9L) {
                pathsToNine[point.toPoint()] = 1
            } else {
                pathsToNine[point.toPoint()] = Grids.neighbors(point.toPoint(), grid).filter{ grid[it.row][it.col] == grid[point.row][point.col] + 1 }.map{ pathsToNine[it]!! }.sum()
            }
        }
        trailHeadScores += pathsToNine[ fill.find { grid[it.row][it.col] == 0L }!!.toPoint() ]!!
    }

    println(trailHeadScores)
}