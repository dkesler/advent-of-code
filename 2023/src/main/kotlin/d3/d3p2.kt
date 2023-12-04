package d3

import utils.*

fun main() {
    val grid = readCharGrid("/d3.txt")

    val gearNeighbors = mutableMapOf<Pair<Int, Int>, List<Int>>()

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if ( grid[y][x].toString().toIntOrNull() != null) {
                if (grid[y].getOrElse(x-1) { _ -> '.' }.toString().toIntOrNull() == null) {
                    val numberStr = grid[y].subList(x, grid[y].size).takeWhile { it.toString().toIntOrNull() != null }.toCharArray()
                    val number = String(numberStr).toInt()

                    val neighbors = neighbors(y, x, x + numberStr.size-1, grid)
                    for (n in neighbors) {
                        if (grid[n.second][n.first] == '*') {
                            gearNeighbors[n] = gearNeighbors.getOrElse(n) { listOf() }.plus(number)
                        }
                    }
                }
            }
        }
    }

    println(gearNeighbors.filter{it.value.size == 2 }.map{ it.value[0] * it.value[1] }.sum())
}