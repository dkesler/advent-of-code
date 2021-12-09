package d9

import utils.readList
import utils.readLongGrid

fun main() {
    val g = readLongGrid("/d9/d9p1")

    val lowPoints = g.mapIndexed{ rowIdx, row ->
        row.mapIndexed{ colIdx, cell ->

            if (rowIdx > 0 && g[rowIdx-1][colIdx] <= cell) Triple(false, rowIdx, colIdx)
            else if (rowIdx < g.size-1 && g[rowIdx+1][colIdx] <= cell) Triple(false, rowIdx, colIdx)
            else if (colIdx > 0 && g[rowIdx][colIdx-1] <= cell) Triple(false, rowIdx, colIdx)
            else if (colIdx < row.size-1 && g[rowIdx][colIdx+1] <= cell) Triple(false, rowIdx, colIdx)
            else Triple(true, rowIdx, colIdx)
        }
    }.flatten().filter{it.first}.map{ Pair(it.second, it.third) }

    val basins = lowPoints.map { findBasin(g, it) }
    val threeLargestBasins = basins.map{it.size}.sortedDescending().take(3)
    println(threeLargestBasins[0] * threeLargestBasins[1] * threeLargestBasins[2])
}

fun findBasin(g: List<List<Long>>, lowPoint: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val toCheck = mutableSetOf(lowPoint)
    val inBasin = mutableSetOf<Pair<Int, Int>>()

    while(toCheck.isNotEmpty()) {
        val next = toCheck.first()
        toCheck.remove(next)

        if (!inBasin.contains(next) && g[next.first][next.second] < 9) {
            inBasin.add(next)

            if (next.first > 0) {
                toCheck.add(Pair(next.first-1, next.second))
            }
            if (next.first < g.size-1 ) {
                toCheck.add(Pair(next.first+1, next.second))
            }

            if (next.second > 0) {
                toCheck.add(Pair(next.first, next.second-1))
            }

            if (next.second < g[0].size-1) {
                toCheck.add(Pair(next.first, next.second+1))
            }
        }
    }

    return inBasin.toSet()
}

