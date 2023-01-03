package d16

import utils.readList
import java.util.PriorityQueue

fun main() {
    val lines = readList("/d16.txt")

    val valves = lines.map {
        val s = it.split(" ", "=", ";")
        Pair(s[1], s[5].toInt())
    }.toMap()
    val openableValves = valves.filter{it.value > 0}.count()

    val edges = lines.map {
        val s = it.split(" ", ",")
        val from = s[1]
        val to = s.subList(9, s.size).filter{ it != "" }
        Pair(from, to)
    }.toMap()

    fun findAllEdgeWeights(edges: Map<String, Collection<String>>): Map<Set<String>, Int> {
        fun findMinPaths(start: String): Map<String, Int> {
            val tentativeDistances = edges.keys.associateWith { if (it == start) 0 else Int.MAX_VALUE }.toMutableMap()
            val visited = mutableSetOf<String>()

            while(tentativeDistances.values.contains(Int.MAX_VALUE)) {
                val current = tentativeDistances.filter{it.key !in visited }.minByOrNull{ it.value }!!
                visited.add(current.key)
                for (neighbor in edges[current.key]!!) {
                    if (neighbor !in visited) {
                        val newTentative = current.value + 1
                        if (newTentative < tentativeDistances[neighbor]!!) tentativeDistances[neighbor] = newTentative
                    }
                }
            }
            return tentativeDistances.toMap()
        }

        val edgeWeights = mutableMapOf<Set<String>, Int>()
        for (start in edges.keys) {
            val minPaths = findMinPaths(start)
            for (path in minPaths) {
                edgeWeights[setOf(start, path.key)] = path.value
            }
        }

        return edgeWeights.toMap()
    }

    val edgeWeights = findAllEdgeWeights(edges)

    //This heuristic is overly generous since it assumes both actors have the same number of minutes equal to the
    //minutes of the one with more minutes left
    fun maxReleasable(minutes: Int, valvesOpened: Set<String>): Long {
        val taking = minutes - (minutes % 2)
        val toOpen = valves.filter{it.key !in valvesOpened}.values.sortedDescending()
                .take(taking)
        return (taking/2 downTo 1).zip( toOpen.windowed(2) ).flatMap{ p -> p.second.map{ (p.first*2 - 1) * it} }.sum().toLong()
    }

    data class State(
            val loc1: String,
            val loc2: String,
            val pressureReleased: Long,
            val maxReleasable: Long,
            val valvesOpened: Map<String, Int>,
            val minuteHumanFree: Int,
            val minuteElephantFree: Int
    )

    val initialState = State("AA", "AA", 0, maxReleasable(26, setOf()), mapOf<String, Int>(), 26, 26)

    val toVisit = PriorityQueue<State> { a, b ->
        b.maxReleasable.compareTo(a.maxReleasable)
    }
    toVisit.add(initialState)

    var bestState = initialState

    val start = System.currentTimeMillis()
    while (toVisit.isNotEmpty()) {
        val visiting = toVisit.poll()!!

        if (visiting.maxReleasable > bestState.pressureReleased && visiting.valvesOpened.size < openableValves ) {
            if (visiting.minuteHumanFree >= visiting.minuteElephantFree) {
                for (valve in valves.filter { it.value > 0 && it.key != visiting.loc1 && it.key !in visiting.valvesOpened}.keys) {
                    val timeToMoveAndOpen = edgeWeights[setOf(visiting.loc1, valve)]!! + 1
                    if (timeToMoveAndOpen <= visiting.minuteHumanFree + 1) {
                        val pressureReleased = visiting.pressureReleased + (visiting.minuteHumanFree - timeToMoveAndOpen) * valves[valve]!!
                        val next = State(
                                valve,
                                visiting.loc2,
                                pressureReleased,
                                pressureReleased + maxReleasable(visiting.minuteElephantFree, visiting.valvesOpened.keys + valve),
                                visiting.valvesOpened + Pair(valve, visiting.minuteHumanFree - timeToMoveAndOpen + 1),
                                visiting.minuteHumanFree - timeToMoveAndOpen,
                                visiting.minuteElephantFree
                        )
                        if (next.pressureReleased > bestState.pressureReleased) {
                            bestState = next
                        }
                        if (next.maxReleasable >= bestState.pressureReleased) {
                            toVisit.offer(next)
                        }
                    }
                }
            } else {
                for (valve in valves.filter { it.value > 0 && it.key != visiting.loc2 && it.key !in visiting.valvesOpened}.keys) {
                    val timeToMoveAndOpen = edgeWeights[setOf(visiting.loc2, valve)]!! +1
                    if (timeToMoveAndOpen <= visiting.minuteElephantFree + 1) {
                        val pressureReleased = visiting.pressureReleased + (visiting.minuteElephantFree - timeToMoveAndOpen) * valves[valve]!!
                        val next = State(
                                visiting.loc1,
                                valve,
                                pressureReleased,
                                pressureReleased + maxReleasable(visiting.minuteHumanFree, visiting.valvesOpened.keys + valve),
                                visiting.valvesOpened + Pair(valve, visiting.minuteElephantFree - timeToMoveAndOpen + 1),
                                visiting.minuteHumanFree,
                                visiting.minuteElephantFree - timeToMoveAndOpen
                        )
                        if (next.pressureReleased > bestState.pressureReleased) {
                            bestState = next
                        }
                        if (next.maxReleasable >= bestState.pressureReleased) {
                            toVisit.offer(next)
                        }
                    }
                }
            }
        }
    }

    println(bestState.pressureReleased)
    println("Elapsed (s): ${(System.currentTimeMillis() - start)/1000}")
}