package d14

import utils.*

fun tiltNorth(grid: List<List<Char>>): List<List<Char>> {
    val m = grid.map{ it.toMutableList() }.toMutableList()

    for (row in m.indices) {
        for (col in m[row].indices) {
            if (m[row][col] == 'O') {
                var newRow = row-1
                while(newRow >= 0 && m[newRow][col] == '.') {
                    m[newRow][col] = 'O'
                    m[newRow+1][col] = '.'
                    newRow--
                }
            }
        }
    }

    return m
}

fun main() {
    val grid = readCharGrid("/d14.txt")

    val tiltedNorth = tiltNorth(grid)

    println(
            tiltedNorth.mapIndexed{ rowIdx, row -> row.map{ if (it == 'O') tiltedNorth.size-rowIdx else 0 } }.flatten().sum()
    )
}
