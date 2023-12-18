package d18

import utils.*

fun main() {
    val lines = readList("/d18.txt")
    val digs = lines.map {
        val s = it.split(" ")
        Pair(s[0], s[1].toInt())
    }

    val trench = mutableSetOf<Point>()
    var cur = Point(0, 0)
    trench.add(cur)
    for (dig in digs) {
        if (dig.first == "U") {
            for (step in (1..dig.second)) {
                cur = Point(cur.row - 1, cur.col)
                trench.add(cur)
            }
        }
        if (dig.first == "D") {
            for (step in (1..dig.second)) {
                cur = Point(cur.row + 1, cur.col)
                trench.add(cur)
            }
        }
        if (dig.first == "L") {
            for (step in (1..dig.second)) {
                cur = Point(cur.row, cur.col - 1)
                trench.add(cur)
            }
        }
        if (dig.first == "R") {
            for (step in (1..dig.second)) {
                cur = Point(cur.row, cur.col + 1)
                trench.add(cur)
            }
        }
    }

    val minRow = trench.minOf{it.row}
    val minCol = trench.minOf{it.col}

    val trenchNormalized = trench.map{ Point(it.row - minRow, it.col - minCol) }
    val rows = trench.maxOf{ it.row} - trench.minOf{it.row} + 1
    val cols = trench.maxOf{ it.col} - trench.minOf{it.col} + 1

    val grid = List(rows, {_ -> List(cols, {'.'})})

    val fills = Grids.fullFloodFill(grid,
            { cur, next -> Point(next.row, next.col) !in trenchNormalized },
            { Point(it.row, it.col) !in trenchNormalized }
    )

    val inside = fills.filter { !it.any { it.row == 0 || it.row == grid.size - 1 || it.col == 0 || it.col == grid[0].size - 1 } }
            .flatten()
    println(
            trenchNormalized.size +
                    inside.size
    )
}
