package d21

import utils.*
import java.math.BigInteger

//20884092248235508586 too high
//619404222304336 too low
//619404222708936 wrong
//619404352585825 wrong
//619404352990425 wrong
//619404352974852 wrong
fun main() {
    val grid = readCharGrid("/d21.txt")

    println((1L..202300-1).filter{ it % 2 == 0L }.map{ it * 4 }.sum())
    println((1L..202300-1).filter{ it % 2 == 1L }.map{ it * 4 }.sum())

    /*
    grid = 131
    steps = 26501365
    X = 202300

    central grid (7567) +
     + odd grid count (7567)  * num full odd grids (40924885400)
     + even grid count (7568)  * num full even grids (40925290000)
     + e grid even count (5710)
     + n grid even count (5718)
     + s grid even count (5716)
     + w grid even count(5724)
     + 202300-1 ne 1.5 grids odd count * size (6627)
     + 202300-1 nw 1.5 grids odd count * size (6658)
     + 202300-1 se 1.5 grids odd count* size (6650)
     + 202300-1 sw 1.5 grids odd count * size (6633)
     + 202300 ne .5 grids even count* size (947)
     + 202300 nw .5 grids even count* size (963)
     + 202300 se .5 grids even count * size (955)
     + 202300 sw .5 grids even count* size (952)

     */

    println(
            BigInteger.valueOf(7567L)
                    + (BigInteger.valueOf(7567L) * BigInteger.valueOf(40924885400))
                    + (BigInteger.valueOf(7568L) * BigInteger.valueOf(40925290000))
                    + BigInteger.valueOf(5710)
                    + BigInteger.valueOf(5718)
                    + BigInteger.valueOf(5716)
                    + BigInteger.valueOf(5724)
                    + BigInteger.valueOf((202300-1) * 6627)
                    + BigInteger.valueOf((202300-1) * 6658)
                    + BigInteger.valueOf((202300-1) * 6650)
                    + BigInteger.valueOf((202300-1) * 6633)
                    + BigInteger.valueOf((202300) * 947)
                    + BigInteger.valueOf((202300) * 963)
                    + BigInteger.valueOf((202300) * 955)
                    + BigInteger.valueOf((202300) * 952)

    )


    val startV = Grids.pointValues(grid).filter{ it.value == 'S' }.first()
    //val start = Point(startV.row, startV.col)
    val start = Point(0, 0)
    //val start = Point(0, 0)
    //val steps = 131*2
    val steps = 130 * 1.5

    val toVisit = mutableSetOf(Pair(0, start))
    val visited = mutableSetOf<Point>()
    val reachable = mutableSetOf<Point>()
    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)

        if ((visiting.first % 2) == 1) reachable.add(visiting.second)

        if (visiting.first < steps) {
            val neighbors = Grids.neighbors(visiting.second, grid)
            val n = neighbors
                    .filter { it !in visited }
                    .filter {
                        val r = if (it.row % grid.size >= 0) it.row % grid.size else it.row % grid.size + grid.size
                        val c = if (it.col % grid[0].size >= 0) it.col % grid[0].size else it.col % grid[0].size + grid[0].size
                        grid[it.row][it.col] != '#'
                    }
                    .map { Pair(visiting.first + 1, it) }
            toVisit.addAll(n)
            visited.addAll(n.map{ it.second})
        }
    }

    println(
            reachable.size
    )

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (Point(row, col) in reachable) print('O') else print(grid[row][col])
        }
        println()
    }






}