package d4

import utils.*

fun main() {
    val grid = readCharGrid("/d4.txt")

    val xmasGroups = mutableSetOf<Set<PointValue<Char>>>()

    for (row in grid.indices) {
        for (col in grid.indices) {
            xmasGroups.addAll(
                xmasesStartingAt2(grid, row, col)
            )
        }
    }

    println(xmasGroups.size)
}



fun xmasesStartingAt2(grid: List<List<Char>>, row: Int, col: Int): Collection<Set<PointValue<Char>>> {
    if (grid[row][col] != 'A') return emptySet()

    val potentialGroups = setOf(
        setOf(PointValue(row, col, 'A'), PointValue(row+1, col+1, 'M'), PointValue(row+1, col-1, 'M'), PointValue(row-1, col+1, 'S'), PointValue(row-1, col-1, 'S')),
        setOf(PointValue(row, col, 'A'), PointValue(row+1, col+1, 'M'), PointValue(row+1, col-1, 'S'), PointValue(row-1, col+1, 'M'), PointValue(row-1, col-1, 'S')),
        setOf(PointValue(row, col, 'A'), PointValue(row+1, col+1, 'S'), PointValue(row+1, col-1, 'M'), PointValue(row-1, col+1, 'S'), PointValue(row-1, col-1, 'M')),
        setOf(PointValue(row, col, 'A'), PointValue(row+1, col+1, 'S'), PointValue(row+1, col-1, 'S'), PointValue(row-1, col+1, 'M'), PointValue(row-1, col-1, 'M')),
    )

    return potentialGroups.filter{
        it.all{ pv -> pv.row in grid.indices && pv.col in grid[pv.row].indices && grid[pv.row][pv.col] == pv.value }
    }
}
