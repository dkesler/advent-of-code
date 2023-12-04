package d3

import utils.*

fun neighbors(y: Int, xMin: Int, xMax: Int,  grid: List<List<Char>>): Set<Pair<Int, Int>> {
    return (xMin-1..xMax+1).flatMap { x ->
        setOf(
            Pair(x, y-1),
            Pair(x, y+1),
    )}
            .plus(Pair(xMin-1, y))
            .plus(Pair(xMax+1, y))
            .filter{ it.second in (grid.indices) && it.first in (grid[it.second].indices) }.toSet()
}

fun main() {
    val grid = readCharGrid("/d3.txt")

    var sum = 0

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if ( grid[y][x].toString().toIntOrNull() != null) {
                if (grid[y].getOrElse(x-1) { _ -> '.' }.toString().toIntOrNull() == null) {
                    val numberStr = grid[y].subList(x, grid[y].size).takeWhile { it.toString().toIntOrNull() != null }.toCharArray()
                    val number = String(numberStr).toInt()

                    val neighbors = neighbors(y, x, x + numberStr.size-1, grid).map{grid[it.second][it.first]}
                    val symbolNeighbors = neighbors.filter{it != '.' && it.toString().toIntOrNull() == null }
                    if (symbolNeighbors.isNotEmpty()) {
                        sum += number
                    }
                }
            }
        }
    }

    println(sum)
}