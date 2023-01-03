package d16

import utils.readList
import java.util.PriorityQueue

fun main() {
    val lines = readList("/d16.txt")

    val valves = lines.map {
        val s = it.split(" ", "=", ";")
        Pair(s[1], s[5].toInt())
    }.toMap()

    val edges = lines.map {
        val s = it.split(" ", ",")
        val from = s[1]
        val to = s.subList(9, s.size).filter{ it != "" }
        Pair(from, to)
    }.toMap()

    fun maxReleasable(minutes: Int, valvesOpened: Set<String>): Long {
        val taking = minutes / 2
        val toOpen = valves.filter{it.key !in valvesOpened}.values.sortedDescending()
                .take(taking)
        return (taking downTo 1).zip(toOpen).map{ (it.first*2 - 1) * it.second }.sum().toLong()
    }

    data class State(
            val loc: String,
            val minutes: Int,
            val pressureReleased: Long,
            val maxReleasable: Long,
            val valvesOpened: Set<String>,
            val visitedSinceLastOpening: Set<String>
    )

    val initialState = State("AA", 30, 0, maxReleasable(30, setOf()), setOf(), setOf() )

    val toVisit = PriorityQueue<State> { a, b ->
        //val c = b.maxReleasable.compareTo(a.maxReleasable)
/*
        if (c == 0) {
            b.pressureReleased.compareTo(a.pressureReleased)
        } else {
            c
        }
*/
        0
    }
    toVisit.add(initialState)

    var bestState = initialState

    while (toVisit.isNotEmpty()) {
        val visiting = toVisit.poll()!!

        if (visiting.minutes > 1 && visiting.maxReleasable > bestState.pressureReleased) {
            if (visiting.loc !in visiting.valvesOpened && valves[visiting.loc]!! > 0) {
                val pressureReleased = visiting.pressureReleased + (visiting.minutes - 1) * valves[visiting.loc]!!.toLong()
                val next = State(
                        visiting.loc,
                        visiting.minutes - 1,
                        pressureReleased,
                        pressureReleased + maxReleasable(visiting.minutes - 1, visiting.valvesOpened + visiting.loc),
                        visiting.valvesOpened + visiting.loc,
                        setOf()
                )
                if (next.maxReleasable > bestState.pressureReleased) {
                    toVisit.offer(next)
                }
                if (next.pressureReleased > bestState.pressureReleased) {
                    bestState = next
                }
            }

            for (dest in edges[visiting.loc]!!) {
                if (dest !in visiting.visitedSinceLastOpening) {
                    val next = State(
                            dest,
                            visiting.minutes - 1,
                            visiting.pressureReleased,
                            visiting.pressureReleased + maxReleasable(visiting.minutes - 1, visiting.valvesOpened),
                            visiting.valvesOpened,
                            visiting.visitedSinceLastOpening + visiting.loc
                    )
                    if (next.maxReleasable > bestState.pressureReleased) {
                        toVisit.offer(next)
                    }
                    if (next.pressureReleased > bestState.pressureReleased) {
                        bestState = next
                    }
                }
            }
        }
    }

    println(bestState.pressureReleased)
}