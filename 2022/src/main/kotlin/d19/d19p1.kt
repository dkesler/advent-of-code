package d19

import utils.readList

data class Blueprint(val id: Int, val oreRobotCost: Int, val clayRobotCost: Int, val obsRobotOreCost: Int, val obsRobotClayCost: Int, val geodeRobotOreCost: Int, val geodeRobotObsCost: Int)
data class State(val mins: Int, val oreRobots: Int, val clayRobots: Int, val obsRobots: Int, val geodeRobots: Int, val ore: Int, val clay: Int, val obs: Int, val geodes: Int)

fun maxGeodes(bp: Blueprint, mins: Int = 24): Int {
    val toVisit = mutableSetOf(State(mins, 1, 0, 0, 0, 0, 0, 0, 0))
    var bestState = toVisit.first()

    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)
        if (visiting.geodes > bestState.geodes) bestState = visiting

        if (visiting.mins == 0) continue

        val newOre = visiting.ore + visiting.oreRobots
        val newClay = visiting.clay + visiting.clayRobots
        val newObs = visiting.obs + visiting.obsRobots
        val newGeodes = visiting.geodes + visiting.geodeRobots

        if (visiting.ore >= bp.oreRobotCost) {
            toVisit.add(State(
                    visiting.mins-1,
                    visiting.oreRobots+1,
                    visiting.clayRobots,
                    visiting.obsRobots,
                    visiting.geodeRobots,
                    newOre - bp.oreRobotCost,
                    newClay,
                    newObs,
                    newGeodes
            ))
        }

        if (visiting.ore >= bp.clayRobotCost) {
            toVisit.add(State(
                    visiting.mins-1,
                    visiting.oreRobots,
                    visiting.clayRobots+1,
                    visiting.obsRobots,
                    visiting.geodeRobots,
                    newOre - bp.clayRobotCost,
                    newClay,
                    newObs,
                    newGeodes
            ))
        }

        if (visiting.ore >= bp.obsRobotOreCost && visiting.clay >= bp.obsRobotClayCost) {
            toVisit.add(State(
                    visiting.mins-1,
                    visiting.oreRobots,
                    visiting.clayRobots,
                    visiting.obsRobots+1,
                    visiting.geodeRobots,
                    newOre - bp.obsRobotOreCost,
                    newClay - bp.obsRobotClayCost,
                    newObs,
                    newGeodes
            ))
        }

        if (visiting.ore >= bp.geodeRobotOreCost && visiting.obs >= bp.geodeRobotObsCost) {
            toVisit.add(State(
                    visiting.mins-1,
                    visiting.oreRobots,
                    visiting.clayRobots,
                    visiting.obsRobots,
                    visiting.geodeRobots+1,
                    newOre - bp.geodeRobotOreCost,
                    newClay,
                    newObs - bp.geodeRobotObsCost,
                    newGeodes
            ))
        }

        toVisit.add(State(
                visiting.mins-1,
                visiting.oreRobots,
                visiting.clayRobots,
                visiting.obsRobots,
                visiting.geodeRobots,
                newOre,
                newClay,
                newObs,
                newGeodes
        ))
    }

    return bestState.geodes
}

fun main() {
    val lines = readList("/d19.txt")
    val blueprints = lines.map {
        val split = it.split(" ", ":")
        Blueprint(
                split[1].toInt(),
                split[7].toInt(),
                split[13].toInt(),
                split[19].toInt(),
                split[22].toInt(),
                split[28].toInt(),
                split[31].toInt()
        )
    }

    println(
            blueprints.map {
                val maxGeodes = maxGeodes2(it)
                maxGeodes * it.id
            }.sum()
    )
}