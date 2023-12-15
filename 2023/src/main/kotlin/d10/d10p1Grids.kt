package d10

import utils.*


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
    val visitedX2 = mutableSetOf<Point>()
    while(cur !in visited) {
        visited.add(cur)
        visitedX2.add(Point(cur.first*2, cur.second*2))
        val next = next(grid, last, cur)

        if (next.first > cur.first) visitedX2.add(Point(cur.first*2+1, cur.second*2))
        if (next.first < cur.first) visitedX2.add(Point(cur.first*2-1, cur.second*2))
        if (next.second > cur.second) visitedX2.add(Point(cur.first*2, cur.second*2 + 1))
        if (next.second < cur.second) visitedX2.add(Point(cur.first*2, cur.second*2 - 1))

        last = cur
        cur = next
    }

    println(visited.size/2)

    val fills = Grids.fullFloodFill(
            gridx2,
            { _, next -> next.toPoint() !in visitedX2 },
            { start -> start.toPoint() !in visitedX2 }
    )

    for (fill in fills) {
        if (fill.any{ it.row == 0 || it.row == gridx2.size-1 || it.col == 0 || it.col == gridx2[it.row].size-1}) {
            fill.forEach{ gridx2[it.row][it.col] = 'O'}
        } else {
            fill.forEach{ gridx2[it.row][it.col] = 'I'}
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
