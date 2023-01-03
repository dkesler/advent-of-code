package d17

import utils.readList
import kotlin.math.max

fun cull(rocks: MutableSet<Pair<Long, Long>>) {
    if (rocks.isEmpty()) return
    val top = rocks.map { it.first }.maxOrNull()!!
    val toVisit = mutableSetOf(Pair(top+1, 0L))
    val visited = mutableSetOf<Pair<Long, Long>>()
    while (toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)
        visited.add(visiting)

        setOf(
                Pair(visiting.first - 1, visiting.second),
                Pair(visiting.first, visiting.second - 1),
                Pair(visiting.first, visiting.second + 1),
        ).filter { it !in toVisit && it !in visited && it !in rocks }
                .filter{ it.first >= 0}
                .filter{ it.second >= 0}
                .filter{it.second <= 6}
                .forEach { toVisit.add(it) }
    }

    rocks.removeIf {
        Pair(it.first + 1, it.second) !in visited &&
                Pair(it.first, it.second - 1) !in visited &&
                Pair(it.first, it.second + 1) !in visited &&
                Pair(it.first - 1, it.second) !in visited
    }
}
data class State(val surface: Set<Pair<Long, Long>>, val shape: Int, val dirIdx: Int)

fun main() {

    val directions = readList("/d17.txt")[0].split("").filter{it != "" }
    val settledRocks = mutableSetOf<Pair<Long, Long>>()
    var jets = 0L
    val topPatterns = mutableMapOf<State, Pair<Long, Long>>()
    val initialState = State(setOf(
            Pair(0, 0),
            Pair(0, 1),
            Pair(0, 2),
            Pair(0, 3),
            Pair(0, 4),
            Pair(0, 5),
            Pair(0, 6)
    ), 0, 0)
    topPatterns[initialState] = Pair(0, -1)

    val start = System.currentTimeMillis()
    var rockIdx = 0L
    while (rockIdx < 1000000000000 ) {
        val shape = (rockIdx) % 5

        val top = settledRocks.map{ it.first }.maxOrNull() ?: -1
        val bottomLeft = Pair(top + 4, 2L)

        var occupied  = occupied(bottomLeft, shape.toInt())
        var settled = false
        while (!settled) {
            //printBoard(settledRocks, occupied)
            val move = directions[ (jets % directions.size).toInt() ]
            jets++
            if (move == ">") {
                if (rightEdge(occupied) <= 5) {
                    val afterRightMove = moveRight(occupied)
                    if (afterRightMove.none{it in settledRocks}) occupied = afterRightMove
                }
            } else if (leftEdge(occupied) > 0) {
                val afterLeftMove = moveLeft(occupied)
                if (afterLeftMove.none{ it in settledRocks}) occupied = afterLeftMove
            }

            val afterMoveDown = moveDown(occupied)
            if (afterMoveDown.any{it in settledRocks} || bottomEdge(afterMoveDown) < 0) {
                settled = true
            } else {
                occupied = afterMoveDown
            }

        }
        settledRocks.addAll(occupied)
        cull(settledRocks)
        val topPattern = settledRocks.map { Pair(it.first - top, it.second) }.toSet()
        val state = State(topPattern, shape.toInt(), (jets % directions.size).toInt())
        if (state in topPatterns.keys) {
            val cycleLength = rockIdx - topPatterns[state]!!.first
            val cycleHeight = top - topPatterns[state]!!.second
            val cyclesToAdd = (1000000000000 - rockIdx) / cycleLength
            val heightToAdd = cycleHeight * cyclesToAdd
            if (cyclesToAdd > 0) {
                settledRocks.addAll(
                        settledRocks.map { Pair(it.first + heightToAdd, it.second) }
                )
                println("Cycle found from ${topPatterns[state]!!.first} to $rockIdx.  Fastforwarding to ${rockIdx + cyclesToAdd * cycleLength}")
                rockIdx += cyclesToAdd * cycleLength
            }
        } else {
            topPatterns[state] = Pair(rockIdx, top)
        }

        rockIdx++
    }

    println(settledRocks.map{ it.first}.maxOrNull()!! + 1)
    println("elapsed (ms): ${System.currentTimeMillis() - start}")
}
