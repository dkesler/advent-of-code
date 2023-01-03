package d24

import d22.Facing
import utils.readCharGrid
import java.util.*
import kotlin.math.abs


/*
test:
start->goal1: 18
goal->start
start->goal2

real:
start->goal1:
goal->start
start->goal2
 */

fun main() {
    val list = readCharGrid("/d24.txt")

    //start->end 1
    //val startMinute = 0
    //val start = Pair(0, 1)
    //val goal = Pair(26, 120)
    //val goal = Pair(5, 6)

    //end-> start
    //val startMinute = 18  //test
    //val startMinute = 274  //real
    //val goal = Pair(0, 1)
    //val start = Pair(26, 120)
    //val start = Pair(5, 6)


    //start -> end 2
    //val startMinute = 41 //test
    val startMinute = 568 //real
    val start = Pair(0, 1)
    val goal = Pair(26, 120)
    //val goal = Pair(5, 6)


    val blizzards = mutableSetOf<Blizzard>()
    val walls = mutableSetOf<Pair<Int, Int>>()
    for (row in list.indices) {
        for (col in list[0].indices) {
            if (list[row][col] in setOf('>', '<', 'v', '^')) {
                val blizzard = when(list[row][col]) {
                    '>' -> Blizzard(row, col, Facing.RIGHT)
                    '<' -> Blizzard(row, col, Facing.LEFT)
                    '^' -> Blizzard(row, col, Facing.UP)
                    else -> Blizzard(row, col, Facing.DOWN)
                }
                blizzards.add(blizzard)
            } else if (list[row][col] == '#') {
                walls.add(Pair(row, col))
            }
        }
    }

    val blizzardsByMinute = mutableMapOf(Pair(0, blizzards))

    fun advanceBlizzards(blizzards: Set<Blizzard>): MutableSet<Blizzard> {
        val newBlizzards = mutableSetOf<Blizzard>()
        for (blizzard in blizzards) {
            val newBlizzard = when(blizzard.facing) {
                Facing.RIGHT -> if (blizzard.col+1 < list[0].size-1 ) Blizzard(blizzard.row, blizzard.col+1, Facing.RIGHT) else Blizzard(blizzard.row, 1, Facing.RIGHT)
                Facing.DOWN -> if (blizzard.row+1 < list.size-1) Blizzard(blizzard.row+1, blizzard.col, Facing.DOWN) else Blizzard(1, blizzard.col, Facing.DOWN)
                Facing.LEFT -> if (blizzard.col-1 > 0) Blizzard(blizzard.row, blizzard.col-1, Facing.LEFT) else Blizzard(blizzard.row, list[0].size-2, Facing.LEFT)
                Facing.UP -> if (blizzard.row-1 > 0) Blizzard(blizzard.row-1, blizzard.col, Facing.UP) else Blizzard(list.size-2, blizzard.col, Facing.UP)
            }
            newBlizzards.add(newBlizzard)
        }

        return newBlizzards
    }

    fun backfillBlizzards(minute: Int) {
        if (minute - 1 !in blizzardsByMinute.keys) backfillBlizzards(minute - 1)
        blizzardsByMinute[minute] = advanceBlizzards(blizzardsByMinute[minute-1]!!)
    }


    data class State(val row: Int, val col: Int, val steps: Int, val path: List<Pair<Int, Int>>)

    val toVisit = PriorityQueue<State> { a, b ->
        /*if (a.steps != b.steps) b.steps.compareTo(a.steps)
        else*/ (abs(a.row - goal.first) + abs(a.col - goal.second)).compareTo(
                abs(b.row - goal.first) + abs(b.col - goal.second)
        )

    }

    toVisit.add(State(start.first, start.second, startMinute, listOf()))
    val visited = mutableSetOf<State>()
    var bestFinish: State? = null

    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()!!
        toVisit.remove()
        visited.add(State(visiting.row, visiting.col, visiting.steps, listOf()))

        if (visiting.steps+1 !in blizzardsByMinute.keys) backfillBlizzards(visiting.steps+1)
        val blizzardLocs = blizzardsByMinute[visiting.steps+1]!!.map{ Pair(it.row, it.col) }.toSet()

        val distToGoal = abs(visiting.row - goal.first) + abs(visiting.col - goal.second)

        if (distToGoal == 0) {
            if (bestFinish == null || visiting.steps < bestFinish.steps) bestFinish = visiting
        } else if (visiting.steps + distToGoal < (bestFinish?.steps ?: Int.MAX_VALUE)) {
            val nextLocs = listOf(
                    State(visiting.row, visiting.col, visiting.steps+1, visiting.path + Pair(visiting.row, visiting.col) ),
                    State(visiting.row - 1, visiting.col, visiting.steps+1, visiting.path + Pair(visiting.row, visiting.col) ),
                    State(visiting.row, visiting.col - 1, visiting.steps+1, visiting.path + Pair(visiting.row, visiting.col) ),
                    State(visiting.row + 1, visiting.col, visiting.steps+1, visiting.path + Pair(visiting.row, visiting.col) ),
                    State(visiting.row, visiting.col + 1, visiting.steps+1, visiting.path + Pair(visiting.row, visiting.col) )
            )
                    .asSequence()
                    .filter { it.row in list.indices }
                    .filter { it.col in list[0].indices }
                    .filter { Pair(it.row, it.col) !in blizzardLocs }
                    .filter { list[it.row][it.col] != '#' }
                    .filter{ State(it.row, it.col, it.steps, listOf()) !in visited }
                    .filter{ it !in toVisit }
                    .toList()
            toVisit.addAll(nextLocs)
        }
    }

    println(bestFinish!!.steps)
}

//228: not right