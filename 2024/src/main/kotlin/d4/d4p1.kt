package d4

import utils.*

fun main() {
    val grid = readCharGrid("/d4.txt")

    val xmasGroups = mutableSetOf<Set<PointValue<Char>>>()

    for (row in grid.indices) {
        for (col in grid.indices) {
            xmasGroups.addAll(
                xmasesStartingAt(grid, row, col)
            )
        }
    }

    println(xmasGroups.size)
}

fun xmasesStartingAt(grid: List<List<Char>>, row: Int, col: Int): Collection<Set<PointValue<Char>>> {
    if (grid[row][col] != 'X') return emptySet()
    val toVisit = mutableSetOf( setOf( PointValue(row, col, grid[row][col])  ))
    val groups = mutableSetOf<Set<PointValue<Char>>>()

    while (toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)
        val x = visiting.find { it.value == 'X' }
        val a = visiting.find { it.value == 'A' }
        val m = visiting.find { it.value == 'M' }

        if (a != null && m != null && x != null) {
            val neighbors = Grids.diagNeighbors(a.toPoint(), grid).filter{ grid[it.row][it.col] == 'S'}.filter{ it.row - a.row == a.row - m.row && it.col - a.col == a.col - m.col }
            groups.addAll(
                neighbors.map{ visiting + PointValue(it.row, it.col, 'S')}
            )
        } else {
            if (m != null && x != null) {
                val neighbors = Grids.diagNeighbors(m.toPoint(), grid).filter{ grid[it.row][it.col] == 'A'}.filter{ it.row - m.row == m.row - x.row && it.col - m.col == m.col - x.col }
                toVisit.addAll(
                    neighbors.map{ visiting + PointValue(it.row, it.col, 'A')}
                )
            } else {

                if (x != null) {
                    val neighbors = Grids.diagNeighbors(x.toPoint(), grid).filter{ grid[it.row][it.col] == 'M'}
                    toVisit.addAll(
                        neighbors.map{ visiting + PointValue(it.row, it.col, 'M')}
                    )
                }
            }
        }

    }




    return groups
}
