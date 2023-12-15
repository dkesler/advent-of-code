package d13

import utils.*

fun isReflectedBelow(grid: List<List<Char>>, idx: Int): Boolean {
    val upper = grid.subList(0, idx+1)
    val lower = grid.subList(idx+1, grid.size)
    return upper.reversed().zip(lower).all{ it.first == it.second }
}

fun isReflectedToRight(grid: List<List<Char>>, idx: Int): Boolean {
    val left = grid.map{ it.subList(0, idx+1)}
    val right = grid.map{ it.subList(idx+1, grid[0].size)}
    val x = left.zip(right).map{ it.first.reversed().zip(it.second) }
    return x.all{ it.all{ it.first == it.second} }
}


fun getHAndVCounts(grid: List<List<Char>>): Pair<Int, Int> {
    for (row in (0..grid.size-2)) {
        if (isReflectedBelow(grid, row)) {
            return Pair(0, row+1 )
        }
    }

    for (col in (0..grid[0].size-2)) {
        if (isReflectedToRight(grid, col)) {
            return Pair(col + 1, 0)
        }
    }

    return Pair(0, 0)
}


fun main() {
    val grid = read3dCharGrid("/d13.txt")

    println(
            grid.map{ getHAndVCounts(it)}.map{ it.first + 100 * it.second }.sum()
    )


    //41911
}

