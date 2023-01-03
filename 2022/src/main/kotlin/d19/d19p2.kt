package d19

import utils.readList

fun maxGeodes2(bp: Blueprint, mins: Int = 24): Int {
    val toVisit = mutableSetOf(State(mins, 1, 0, 0, 0, 0, 0, 0, 0))
    var bestState = toVisit.first()

    val maxOreCost = listOf(bp.clayRobotCost, bp.oreRobotCost, bp.obsRobotOreCost, bp.geodeRobotOreCost).maxOrNull()!!

    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)
        if (visiting.geodes > bestState.geodes) bestState = visiting

        if (visiting.mins == 0) continue

        val newOre = visiting.ore + visiting.oreRobots
        val newClay = visiting.clay + visiting.clayRobots
        val newObs = visiting.obs + visiting.obsRobots
        val newGeodes = visiting.geodes + visiting.geodeRobots

        var couldBuildGeo = false
        if (visiting.ore >= bp.geodeRobotOreCost && visiting.obs >= bp.geodeRobotObsCost) {
            couldBuildGeo = true
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

        //if i am making more obs per minute than i can spend, stop building
        var couldBuildObs = false
        if (visiting.ore >= bp.obsRobotOreCost && visiting.clay >= bp.obsRobotClayCost && visiting.obsRobots < bp.geodeRobotObsCost) {
            couldBuildObs = true
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

        //if i am making more clay per minute than i can spend, stop building
        var couldBuildClay = false
        if (visiting.ore >= bp.clayRobotCost && visiting.clayRobots < bp.obsRobotClayCost) {
            couldBuildClay = true
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

        //if i am making more ore per minute than i can spend, stop building
        var couldBuildOre = false
        if (visiting.ore >= bp.oreRobotCost && visiting.oreRobots < maxOreCost) {
            couldBuildOre = true
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



        val canBuildEverything = couldBuildOre && couldBuildClay && couldBuildObs && couldBuildGeo

        val doNotSkip = canBuildEverything ||
                (visiting.obsRobots >= bp.geodeRobotObsCost && visiting.oreRobots >= maxOreCost && couldBuildGeo) ||
                (visiting.clayRobots >= bp.obsRobotClayCost && visiting.oreRobots >= maxOreCost && couldBuildObs) ||
                (visiting.oreRobots >= maxOreCost && couldBuildClay)

        if (!doNotSkip) {
            toVisit.add(State(
                    visiting.mins - 1,
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
    }.take(3)

    val maxGeodesPerBlueprint = blueprints.map {
        val maxGeodes = maxGeodes2(it, 32)
        maxGeodes
    }
    println(
            maxGeodesPerBlueprint.reduce{ a, b -> a*b }
    )
}