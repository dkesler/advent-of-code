package d8

import utils.*
import kotlin.math.sqrt

fun main() {
    val grid = readCharGrid("/d8.txt")

    val antinodes = findAntinodes2(grid)

    println(antinodes.size)
    println(antinodes)
}

fun findAntinodes2(grid: List<List<Char>>): Set<Point> {
    val antinodes = mutableSetOf<Point>()
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (isAntinode2(row, col, grid)) {
                antinodes.add(Point(row, col))
            }


        }
    }

    return antinodes.toSet()
}

fun isAntinode2(row: Int, col: Int, grid: List<List<Char>>): Boolean {
    val antennae = mutableListOf<Pair<Char, Double>>()
    for (aRow in grid.indices) {
        for (aCol in grid[row].indices) {
            if (grid[aRow][aCol] != '.' && (row != aRow || col != aCol)) {
                if (grid[aRow][aCol] == grid[row][col]) return true
                val angle = if (col != aCol) (row - aRow) / (col - aCol).toDouble() else if (row > aRow) Double.MAX_VALUE else Double.MIN_VALUE
                antennae.add(Pair(grid[aRow][aCol], angle))
            }
        }
    }

    val aMap = antennae.groupBy { it.first }

    for (f in aMap.keys) {
        val ants = aMap[f]!!
        for (ant in ants) {
            if (ants.count { it == Pair(ant.first, ant.second)} == 2) return true
        }
    }

    return false



}
