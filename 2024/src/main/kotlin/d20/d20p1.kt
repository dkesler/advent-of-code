package d20

import d16.aStar2
import utils.Grids
import utils.readCharGrid
import kotlin.math.min

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

    for (point in Grids.pointValues(grid).filter{it.value == '#'}) {
        val neighbors = Grids.neighbors(point.toPoint(), grid).filter{grid[it.row][it.col] != '#' }
        var saved100 = false
        for (n1 in neighbors) {
            for (n2 in neighbors) {
                if (n1 != n2) {
                    val oneToTwo = forward[n1]!!.cost + reverse[n2]!!.cost + 2
                    val twoToOne = forward[n2]!!.cost + reverse[n1]!!.cost + 2
                    if (basecost - min(oneToTwo, twoToOne) >= 100) saved100 = true
                }
            }
        }

        if (saved100) saved++
    }

    println(saved)




}