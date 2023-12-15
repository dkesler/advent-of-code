package d10

import utils.*

fun neighbors(pair: Pair<Int, Int>): Set<Pair<Int, Int>> {
    return setOf(
            Pair(pair.first, pair.second-1),
            Pair(pair.first, pair.second+1),
            Pair(pair.first-1, pair.second),
            Pair(pair.first+1, pair.second)
    )
}

fun main() {
    val grid = readCharGrid("/d10.txt").map{ it.toMutableList() }.toMutableList()

    val gridx2 = (0 until grid.size*2).map {
        MutableList(grid[0].size*2, { _ -> '.'} )
    }.toMutableList()
    grid.indices.forEach { row ->
        grid[row].indices.forEach { col ->
            gridx2[row*2][col*2] = grid[row][col]
        }
    }

    var cur = find('S', grid)
    var last = cur
    val visited = mutableSetOf<Pair<Int, Int>>()
    val visitedX2 = mutableSetOf<Pair<Int, Int>>()
    while(cur !in visited) {
        visited.add(cur)
        visitedX2.add(Pair(cur.first*2, cur.second*2))
        val next = next(grid, last, cur)

        if (next.first > cur.first) visitedX2.add(Pair(cur.first*2+1, cur.second*2))
        if (next.first < cur.first) visitedX2.add(Pair(cur.first*2-1, cur.second*2))
        if (next.second > cur.second) visitedX2.add(Pair(cur.first*2, cur.second*2 + 1))
        if (next.second < cur.second) visitedX2.add(Pair(cur.first*2, cur.second*2 - 1))

        last = cur
        cur = next
    }

    println(visited.size/2)


    for (row in gridx2.indices) {
        for (col in gridx2[row].indices) {
            if (Pair(row, col) !in visitedX2 && gridx2[row][col] !in setOf('I', 'O')) {
                val visited3 = mutableSetOf<Pair<Int, Int>>()
                val toVisit = mutableSetOf(Pair(row, col))
                while(toVisit.isNotEmpty()) {
                    val visiting = toVisit.first()
                    toVisit.remove(visiting)
                    visited3.add(visiting)

                    toVisit.addAll(
                            neighbors(visiting)
                                    .filter{ it.first in gridx2.indices && it.second in gridx2[it.first].indices }
                                    .filter{ it !in visitedX2 }
                                    .filter{ it !in visited3 }
                    )
                }

                if (visited3.any{ it.first == 0 || it.first == gridx2.size-1 || it.second == 0 || it.second == gridx2[it.first].size-1}) {
                    visited3.forEach{ gridx2[it.first][it.second] = 'O'}
                } else {
                    visited3.forEach{ gridx2[it.first][it.second] = 'I'}
                }
            }
        }
    }

    var inCt = 0
    for (r in grid.indices) {
        for (c in grid[r].indices) {
            if (Pair(r, c) !in visited) {
                if (gridx2[r*2][c*2] == 'I') inCt++
            }
        }
    }

    println(inCt)
}
fun next(grid: List<List<Char>>, last: Pair<Int, Int>, cur: Pair<Int, Int>): Pair<Int, Int> {
    val curChar = grid[cur.first][cur.second]
    return when(curChar) {
        '-' -> if (last.second < cur.second) Pair(cur.first, cur.second+1) else Pair(cur.first, cur.second-1)
        '|' -> if (last.first < cur.first) Pair(cur.first + 1, cur.second) else Pair(cur.first-1, cur.second)
        'F' -> if (last.first == cur.first) Pair(cur.first+1, cur.second) else Pair(cur.first, cur.second+1)
        'J' -> if (last.first == cur.first) Pair(cur.first-1, cur.second) else Pair(cur.first, cur.second-1)
        '7' -> if (last.first == cur.first) Pair(cur.first+1, cur.second) else Pair(cur.first, cur.second-1)
        'L' -> if (last.first == cur.first) Pair(cur.first-1, cur.second) else Pair(cur.first, cur.second+1)
        else -> {
            if (grid[cur.first - 1][cur.second] in setOf('|', 'F', '7')) Pair(cur.first - 1, cur.second)
            else if (grid[cur.first + 1][cur.second] in setOf('|', 'J', 'L')) Pair(cur.first + 1, cur.second)
            else if (grid[cur.first][cur.second - 1] in setOf('-', 'L', 'F')) Pair(cur.first, cur.second - 1)
            else Pair(cur.first, cur.second + 1)
        }
    }
}

fun find(c: Char, grid: List<List<Char>>): Pair<Int, Int> {
    for (row in grid.indices)
        for (col in grid[row].indices)
            if (grid[row][col] == c) return Pair(row ,col)

    return Pair(-1, -1)
}
