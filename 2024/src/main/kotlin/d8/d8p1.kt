package d8

import utils.*
import kotlin.math.sqrt

fun main() {
    val grid = readCharGrid("/d8.txt")

    val antinodes = findAntinodes(grid)

    println(antinodes.size)
    println(antinodes)
}

fun findAntinodes(grid: List<List<Char>>): Set<Point> {
    val antinodes = mutableSetOf<Point>()
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (isAntinode(row, col, grid)) {
                antinodes.add(Point(row, col))
            }


        }
    }

    return antinodes.toSet()
}

fun isAntinode(row: Int, col: Int, grid: List<List<Char>>): Boolean {
    val antennae = mutableSetOf<Triple<Char, Double, Double>>()
    for (aRow in grid.indices) {
        for (aCol in grid[row].indices) {
            if (grid[aRow][aCol] != '.' && (aRow != row || aCol != col)) {
                val dist = sqrt( (row - aRow).toDouble() * (row - aRow) + (col - aCol) * (col - aCol))
                val angle = if (col != aCol) (row - aRow) / (col - aCol).toDouble() else if (row > aRow) Double.MAX_VALUE else Double.MIN_VALUE
                antennae.add(Triple(grid[aRow][aCol], dist, angle))
            }
        }
    }

    val aMap = antennae.groupBy { it.first }

    for (f in aMap.keys) {
        val ants = aMap[f]!!.toSet()
        for (ant in ants) {
            if (ants.contains(Triple(ant.first, ant.second * 2, ant.third))) return true
        }
    }

    return false



}
