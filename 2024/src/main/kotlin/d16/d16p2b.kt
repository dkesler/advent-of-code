package d16

import utils.*
import java.util.*


fun main() {
    val grid = readCharGrid("/d16.txt")

    val start = Grids.pointValues(grid).filter{ it.value == 'S' }.first().toPoint()
    val end  = Grids.pointValues(grid).filter{ it.value == 'E' }.first().toPoint()

    val target = 122492L

    val neighbors = { cur: Pair<Point, String> ->
        val next = getNext(cur)
        val rotated = getRotations(cur)
        if (grid[next.first.row][next.first.col] != '#')
            rotated + next
        else
            rotated
    }
    val cost: (Pair<Point, String>, Pair<Point, String>) -> Long = { cur: Pair<Point, String>, next: Pair<Point, String> -> if (cur.second == next.second) 1 else 1000 }

    val bestMapFromStart = aStar2(
        setOf(Pair(start, "E")),
        { it == Pair(end, "E") },
        neighbors,
        cost,
        { 0 }
    )

    val bestMapFromEnd = aStar2(
        setOf(Pair(end, "W")),
        { it == Pair(start, "W") },
        neighbors,
        cost,
        { 0 }
    )

    val bestSet = mutableSetOf<Pair<Point, String>>()

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] != '#') {
                for (dir in setOf("N", "S", "E", "W")) {
                    val intermediateFromStart = Pair(Point(row, col), dir)
                    val intermediateFromEnd = Pair(Point(row, col), opposite(dir))
                    if (bestMapFromStart[intermediateFromStart]!!.cost + bestMapFromEnd[intermediateFromEnd]!!.cost == target) {
                        bestSet.add(intermediateFromStart)
                    }
                }
            }
        }
    }


    println(bestSet.map{it.first}.toSet().size)

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (Point(row, col) in bestSet.map{ it.first } ) {
                print("O")
            } else {
                print(grid[row][col])
            }
        }
        println("")
    }

}

fun opposite(dir: String): String {
    return if (dir == "N") {
        "S"
    } else if (dir == "S") {
        "N"
    } else if (dir == "E") {
        "W"
    } else {
        "E"
    }
}
//wrong: 519
