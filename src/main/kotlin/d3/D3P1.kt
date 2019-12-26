package d3

import java.io.File
import kotlin.math.abs

fun main() {
    val wires = File("src/main/resources/d3/d3p1").readLines()
        .map(::toWire)

    println(findMinCrossingDistance(wires[0], wires[1]))
}

fun findMinCrossingDistance(w1: List<Move>, w2: List<Move>): Int? {
    val points: List<Crossing> = findCrossings(w1, w2)
    return points.map{abs(it.x) + abs(it.y)}.min()
}

fun findCrossings(w1: List<Move>, w2: List<Move>): List<Crossing> {
    val pointsOccupiedBy1: Map<Pair<Int, Int>, Int> = findOccupied(w1)
    val pointsOccupiedBy2: Map<Pair<Int, Int>, Int> = findOccupied(w2)

    return pointsOccupiedBy2.filter{pointsOccupiedBy1.containsKey(it.key)}
        .map{ Crossing(it.key.first, it.key.second, it.value + pointsOccupiedBy1.getValue(it.key)) }
}

fun findOccupied(wire: List<Move>): Map<Pair<Int, Int>, Int> {
    return wire.fold(Grid(), Grid::withMove).occupied
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;
}

fun toWire(str: String): List<Move> {
    return str.trim().split(',')
        .map(::toMove)
}

fun fromChar(ch: Char): Direction {
    return when(ch) {
        'U' -> Direction.UP
        'D' -> Direction.DOWN
        'L' -> Direction.LEFT
        'R' -> Direction.RIGHT
        else -> throw RuntimeException("Unexpected direction character " + ch);
    }
}
data class Crossing(val x: Int, val y: Int, val timing: Int)
data class Move(val direction: Direction, val amount: Int)

fun toMove(str: String): Move {
    val direction = fromChar(str[0])
    val amount = Integer.parseInt(str.substring(1))
    return Move(direction, amount)
}

class Grid(val x: Int, val y : Int, val cycles: Int, val occupied: Map<Pair<Int, Int>, Int>) {
    constructor() : this(0, 0, 0, HashMap())

    fun withMove(move: Move): Grid {
        val step: Pair<Int, Int> =  when(move.direction) {
            Direction.UP -> Pair(0, -1)
            Direction.DOWN -> Pair(0, 1)
            Direction.LEFT -> Pair(-1, 0)
            Direction.RIGHT -> Pair(1, 0)
        }

        val newlyOccupied = (1..move.amount)
            .map{ Triple(x + step.first*it, y + step.second*it, cycles + it) }
            .filter { !occupied.containsKey(Pair(it.first, it.second)) }
            .map{ Pair(Pair(it.first, it.second), it.third) }

        return Grid(x + step.first*move.amount, y + step.second*move.amount, cycles + move.amount, occupied.plus(newlyOccupied))
    }
}
