package d10

import utils.*

fun main() {
    val grid = readLongGrid("/d10.txt")

    var trailHeadScores = 0L

    for (possibleTrailHead in Grids.pointValues(grid).filter{it.value == 0L}) {
        val fill = Grids.floodfill(
            grid,
            possibleTrailHead.toPoint(),
            { from, to -> from.value == to.value - 1 },
        )

        trailHeadScores += fill.filter{ grid[it.row][it.col] == 9L }.count()
    }

    println(trailHeadScores)


}