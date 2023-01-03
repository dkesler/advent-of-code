package d17

import utils.readList
import kotlin.math.max

fun occupied(bottomLeft: Pair<Long, Long>, shape: Int): Set<Pair<Long, Long>> {
    if (shape == 0) {
        return (0..3).map { Pair(bottomLeft.first, bottomLeft.second + it)}.toSet()
    } else if (shape == 1) {
        return setOf(
                Pair(bottomLeft.first+0, bottomLeft.second+1),
                Pair(bottomLeft.first+1, bottomLeft.second),
                Pair(bottomLeft.first+1, bottomLeft.second+1),
                Pair(bottomLeft.first+2, bottomLeft.second+1),
                Pair(bottomLeft.first+1, bottomLeft.second+2)
        )
    } else if (shape == 2) {
        return setOf(
                bottomLeft,
                Pair(bottomLeft.first, bottomLeft.second+1),
                Pair(bottomLeft.first, bottomLeft.second+2),
                Pair(bottomLeft.first+1, bottomLeft.second+2),
                Pair(bottomLeft.first+2, bottomLeft.second+2)
        )
    } else if (shape == 3) {
        return (0..3).map { Pair(bottomLeft.first + it, bottomLeft.second)}.toSet()
    } else {
        return setOf(
                bottomLeft,
                Pair(bottomLeft.first + 1, bottomLeft.second),
                Pair(bottomLeft.first, bottomLeft.second + 1),
                Pair(bottomLeft.first + 1, bottomLeft.second + 1)
        )
    }
}

fun rightEdge(points: Set<Pair<Long, Long>>): Long {
    return points.map{ it.second}.maxOrNull()!!
}

fun leftEdge(points: Set<Pair<Long, Long>>): Long {
    return points.map{ it.second}.minOrNull()!!
}

fun bottomEdge(points: Set<Pair<Long, Long>>): Long {
    return points.map{ it.first}.minOrNull()!!
}


fun moveRight(points: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    return points.map{ Pair(it.first, it.second + 1)}.toSet()
}

fun moveLeft(points: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    return points.map{ Pair(it.first, it.second - 1)}.toSet()
}

fun moveDown(points: Set<Pair<Long, Long>>): Set<Pair<Long, Long>> {
    return points.map{ Pair(it.first - 1, it.second)}.toSet()
}


fun main() {
    val directions = readList("/d17.txt")[0].split("").filter{it != "" }
    val settledRocks = mutableSetOf<Pair<Long, Long>>()
    var jets = 0

    for (rockIdx in 1..2022) {
        val shape = (rockIdx-1) % 5

        val top = settledRocks.map{ it.first }.maxOrNull() ?: -1

        val bottomLeft = Pair(top + 4, 2L)

        var occupied  = occupied(bottomLeft, shape.toInt())
        var settled = false
        while (!settled) {
            //prLongBoard(settledRocks, occupied)
            val move = directions[ jets % directions.size ]
            jets++
            if (move == ">") {
                if (rightEdge(occupied) <= 5) {
                    val afterRightMove = moveRight(occupied)
                    if (afterRightMove.intersect(settledRocks).isEmpty()) occupied = afterRightMove

                }
            } else if (leftEdge(occupied) > 0) {
                val afterLeftMove = moveLeft(occupied)
                if (afterLeftMove.intersect(settledRocks).isEmpty()) occupied = afterLeftMove
            }

            val afterMoveDown = moveDown(occupied)

            if (afterMoveDown.intersect(settledRocks).isNotEmpty() || bottomEdge(afterMoveDown) < 0) {
                settled = true
            } else {
                occupied = afterMoveDown
            }

        }
        settledRocks.addAll(occupied)


    }

    println(settledRocks.map{ it.first}.maxOrNull()!! + 1)
}

fun printBoard(settledRocks: MutableSet<Pair<Long, Long>>, occupied: Set<Pair<Long, Long>>) {
    val settledTop = settledRocks.map { it.first }.maxOrNull() ?: 0
    val top = max(settledTop, occupied.map{it.first}.maxOrNull()!!)
    val bottom = settledRocks.map { it.first }.minOrNull() ?: 0

    for (row in top downTo bottom) {
        for (col in 0..6) {
            if (Pair(row, col) in settledRocks) {
                print('#')
            } else if (Pair(row, col) in occupied) {
                print('@')
            } else {
                print('.')
            }
        }
        println("")
    }
    println("-------")
    println("")
}
