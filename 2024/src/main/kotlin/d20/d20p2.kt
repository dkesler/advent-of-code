package d20

import d16.aStar2
import utils.*
import kotlin.math.abs

fun manhattan(p1: Point, p2: Point): Int {
    return abs(p1.col - p2.col) + abs(p1.row - p2.row)
}

fun main() {
    val grid = readCharGrid("/d20.txt")
    val start = Grids.pointValues(grid).filter{ it.value == 'S'}.map{it.toPoint()}.first()
    val end = Grids.pointValues(grid).filter{ it.value == 'E'}.map{it.toPoint()}.first()

    val forward = aStar2(
        setOf(start),
        { it == end },
        { cur -> Grids.neighbors(cur, grid).filter{grid[it.row][it.col] != '#' }.toSet() },
        { _, _ -> 1 },
        {0}
    )

    val reverse = aStar2(
        setOf(end),
        { it == start },
        { cur -> Grids.neighbors(cur, grid).filter{grid[it.row][it.col] != '#' }.toSet() },
        { _, _ -> 1 },
        {0}
    )


    val basecost = forward[end]!!.cost

    var saved = 0

    for (cheatStart in Grids.pointValues(grid).filter{it.value != '#'}) {
        for (rowCheat in -20..20) {
            for (colCheat in (- (20 - abs(rowCheat))..(20-abs(rowCheat)))) {
                val cheatEnd = Point(cheatStart.row + rowCheat, cheatStart.col + colCheat)
                if (cheatStart.toPoint() != cheatEnd && cheatEnd.row in grid.indices && cheatEnd.col in grid[0].indices && grid[cheatEnd.row][cheatEnd.col] != '#') {
                    val shortcutcost = forward[cheatStart.toPoint()]!!.cost + reverse[cheatEnd]!!.cost + manhattan(cheatStart.toPoint(), cheatEnd)
                    val timeSaved = basecost - shortcutcost
                    if (timeSaved >= 100) saved++
                }
            }
        }
    }

    println(saved)




}
