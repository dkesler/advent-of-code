package d14

import utils.*

fun tiltSouth(grid: List<List<Char>>): List<List<Char>> {
    val m = grid.map{ it.toMutableList() }.toMutableList()

    for (row in m.indices.reversed()) {
        for (col in m[row].indices) {
            if (m[row][col] == 'O') {
                var newRow = row+1
                while(newRow < m.size && m[newRow][col] == '.') {
                    m[newRow][col] = 'O'
                    m[newRow-1][col] = '.'
                    newRow++
                }
            }
        }
    }

    return m
}

fun tiltEast(grid: List<List<Char>>): List<List<Char>> {
    val m = grid.map{ it.toMutableList() }.toMutableList()

    for (col in m[0].indices.reversed()) {
        for (row in m.indices) {
            if (m[row][col] == 'O') {
                var newCol = col+1
                while(newCol < m[col].size && m[row][newCol] == '.') {
                    m[row][newCol] = 'O'
                    m[row][newCol-1] = '.'
                    newCol++
                }
            }
        }
    }

    return m
}

fun tiltWest(grid: List<List<Char>>): List<List<Char>> {
    val m = grid.map{ it.toMutableList() }.toMutableList()

    for (col in m[0].indices) {
        for (row in m.indices) {
            if (m[row][col] == 'O') {
                var newCol = col-1
                while(newCol >= 0 && m[row][newCol] == '.') {
                    m[row][newCol] = 'O'
                    m[row][newCol+1] = '.'
                    newCol--
                }
            }
        }
    }

    return m
}

fun main() {
    val grid = readCharGrid("/d14.txt")

    var tilted = grid

    val seen = mutableMapOf<List<List<Char>>, Int>()
    val cycles = 1000000000-1

    var i = 1
    var fastforwarded = false


    while (i <= cycles) {
        tilted = tiltNorth(tilted)
        tilted = tiltWest(tilted)
        tilted = tiltSouth(tilted)
        tilted = tiltEast(tilted)

        if (tilted in seen.keys && !fastforwarded) {
            val cycleLength = i - seen[tilted]!!
            i = i + ( (cycles - i) / cycleLength) * cycleLength
            fastforwarded = true
        } else {
            seen[tilted] = i
            i += 1
        }
    }

/*    for (row in tilted) {
        for (col in row) {
            print(col)
        }
        println()
    }*/

    println(
            tilted.mapIndexed{ rowIdx, row -> row.map{ if (it == 'O') tilted.size-rowIdx else 0 } }.flatten().sum()
    )
}
