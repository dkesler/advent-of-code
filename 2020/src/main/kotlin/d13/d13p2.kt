package d13

fun findCycle(c1: Cycle, c2: Cycle): Cycle {
    if (c2.fullCycle() > c1.fullCycle()) {
        return findCycle(c2, c1)
    }
    var i = 1
    val c1FullCycle = c1.fullCycle()
    val c2FullCycle = c2.fullCycle()

    while(true) {
        val t = c1FullCycle * i
        val interval = c2.start - c1.start
        if ( (t - interval) % c2FullCycle == 0L) {
            return Cycle(t + c1.start, c1.cycleComponents + c2.cycleComponents)
        }
        ++i
    }
}
data class Cycle(val start: Long, val cycleComponents: Set<Long>) {
    fun fullCycle(): Long {
        return cycleComponents.fold(1L, {i, j -> i*j})
    }
}

fun generateInitialCycle(id1: Pair<Long, Long>, id2: Pair<Long, Long>): Cycle {
    var i = 1

    while(true) {
        val t = id1.second * i
        val interval = id2.first - id1.first
        if ( (t + interval) % id2.second == 0L) {
            return Cycle(t, setOf(id1.second, id2.second))
        }
        ++i
    }
}


fun main() {
    val content = Class::class.java.getResource("/d13/d13p1").readText()
    val list = content.split("\n").filter{it != ""}

    val busIds = list[1].split(",").mapIndexed{
        idx, busId -> Pair(idx.toLong(), busId)
    }
        .filter{ it.second != "x"}
        .map{ Pair(it.first, it.second.toLong()) }

    val firstBus = busIds[0]
    val rest = busIds.subList(1, busIds.size)
    val cycles = rest.map{ generateInitialCycle(firstBus, it) }

    val firstCycle = cycles[0]
    val restCycles = cycles.subList(1, cycles.size)
    val mergedCycle = restCycles.fold(
        firstCycle,
        ::findCycle
    )

    println(mergedCycle)
}
