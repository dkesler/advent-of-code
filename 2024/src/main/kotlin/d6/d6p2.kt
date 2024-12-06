package d6

import utils.*

fun main() {
    val grid = readCharGrid("/d6.txt")

    var row = 43
    var col = 79
    //var row = 6
    //var col = 4
    var dir = 'U'
    val visited = mutableSetOf(Triple(row, col, dir))
    var successfulObstacles = 0

    for (obstacle in Grids.points(grid).filter{ grid[it.row][it.col] != '^' }) {

        visited.clear()
        dir = 'U'
        row = 43
        col = 79
        //row = 6
        //col = 4

        while (row in grid.indices && col in grid[row].indices && Triple(row, col, dir) !in visited) {
            visited.add(Triple(row, col, dir))
            if (dir == 'U') {
                val nextRow = row-1
                val nextCol = col
                if (nextRow in grid.indices && nextCol in grid[nextRow].indices) {
                    if  (grid[nextRow][nextCol] != '#' && obstacle != Point(nextRow, nextCol)) {
                        row = nextRow
                        col = nextCol
                    } else {
                        dir = 'R'
                    }
                } else {
                    row = nextRow
                    col = nextCol
                }
            } else if (dir == 'R') {
                val nextRow = row
                val nextCol = col+1
                if (nextRow in grid.indices && nextCol in grid[nextRow].indices) {
                    if  (grid[nextRow][nextCol] != '#' && obstacle != Point(nextRow, nextCol)) {
                        row = nextRow
                        col = nextCol
                    } else {
                        dir = 'D'
                    }
                } else {
                    row = nextRow
                    col = nextCol
                }
            } else if (dir == 'D') {
                val nextRow = row+1
                val nextCol = col
                if (nextRow in grid.indices && nextCol in grid[nextRow].indices) {
                    if  (grid[nextRow][nextCol] != '#' && obstacle != Point(nextRow, nextCol)) {
                        row = nextRow
                        col = nextCol
                    } else {
                        dir = 'L'
                    }
                } else {
                    row = nextRow
                    col = nextCol
                }
            } else if (dir == 'L') {
                val nextRow = row
                val nextCol = col-1
                if (nextRow in grid.indices && nextCol in grid[nextRow].indices) {
                    if  (grid[nextRow][nextCol] != '#' && obstacle != Point(nextRow, nextCol)) {
                        row = nextRow
                        col = nextCol
                    } else {
                        dir = 'U'
                    }
                } else {
                    row = nextRow
                    col = nextCol
                }
            }
        }

        if (row in grid.indices && col in grid[row].indices) {
            successfulObstacles++
        }

    }

    println(successfulObstacles)


}