package d13

import utils.*

private fun findNewAxes(grid: MutableList<MutableList<Char>>, defaultAxes: Pair<Set<Int>, Set<Int>>): Pair<Set<Int>, Set<Int>> {
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] == '.') grid[row][col] = '#' else grid[row][col] = '.'

            val newAxes = findAxes(grid)
            if ((newAxes.first - defaultAxes.first).isNotEmpty() || (newAxes.second - defaultAxes.second).isNotEmpty()) {
                return Pair(newAxes.first - defaultAxes.first, newAxes.second - defaultAxes.second)
            }

            if (grid[row][col] == '.') grid[row][col] = '#' else grid[row][col] = '.'
        }
    }

    throw Error()
}

fun findAxes(grid: List<List<Char>>): Pair<Set<Int>, Set<Int>> {
    val hLines = mutableSetOf<Int>()
    val vLines = mutableSetOf<Int>()
    for (row in (0..grid.size-2)) {
        if (isReflectedBelow(grid, row)) {
            hLines.add(row)
        }
    }

    for (col in (0..grid[0].size-2)) {
        if (isReflectedToRight(grid, col)) {
            vLines.add(col)
        }
    }

    return Pair(hLines, vLines)
}

fun main() {
    val grids = read3dCharGrid("/d13.txt").map {
        it.map { it.toMutableList() }.toMutableList()
    }

    val defaultAxes = grids.map{ findAxes(it) }
    val newAxes = grids.mapIndexed { idx, grid ->
        findNewAxes(grid, defaultAxes[idx])
    }


    println(
            newAxes.map{ if (it.first.isNotEmpty()) (it.first.first()+1) * 100 else it.second.first()+1 }.sum()
    )

}

