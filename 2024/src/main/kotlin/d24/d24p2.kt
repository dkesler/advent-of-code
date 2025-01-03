package d24

import utils.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.random.Random

fun main() {

    val blocks = readBlocks("/d24.txt")


    //val wireVals = blocks[0].map{ it.split(":") }.map{ Pair(it[0], it[1].trim().toInt())}.toMap().toMutableMap()

    val gates = blocks[1].map{ it.split(" ")}.map{ Gate(it[0], it[2] ,it[1], it[4])}
    val gatesByOut = gates.associateBy{it.out}

    val permaSwaps = mapOf(
        Pair(gatesByOut["vdc"]!!, gatesByOut["z12"]!!),
        Pair(gatesByOut["z12"]!!, gatesByOut["vdc"]!!),
        Pair(gatesByOut["z21"]!!, gatesByOut["nhn"]!!),
        Pair(gatesByOut["nhn"]!!, gatesByOut["z21"]!!),
        Pair(gatesByOut["tvb"]!!, gatesByOut["khg"]!!),
        Pair(gatesByOut["khg"]!!, gatesByOut["tvb"]!!),
        Pair(gatesByOut["z33"]!!, gatesByOut["gst"]!!),
        Pair(gatesByOut["gst"]!!, gatesByOut["z33"]!!),
    )

    println(permaSwaps.keys.map{ it.out }.sorted().joinToString(","))


    val dirtyZIndices = trial(gates, permaSwaps)

    //println(dirtyZIndices.size)

    val testZIndex = dirtyZIndices.sorted().take(1)
    val gatesFeedingTestZIndex = findWiresFeedingDirtyZs(gates, testZIndex)
    val knownCleanGates = if (testZIndex.isNotEmpty()) findWiresFeedingDirtyZs(gates, (0..testZIndex[0]-1).toList()) else setOf()
    //val gatesFeedingAllDirtyZIndices = findWiresFeedingDirtyZs(gates, dirtyZIndices)

    //println(gatesFeedingDirtyZs.size)

    for (dirtyGate in gatesFeedingTestZIndex.filter{ it !in knownCleanGates} ) {
        for (testGate in gates.filter{ it !in knownCleanGates}) {
            val swaps = mutableMapOf(
                Pair(dirtyGate, testGate),
                Pair(testGate, dirtyGate)
            )
            val testDirtyZIndices = trial(gates, swaps + permaSwaps)
            if (!testDirtyZIndices.contains(testZIndex[0])) println(swaps)
        }
    }


}

private fun trial(gates: List<Gate>, swaps: Map<Gate, Gate>): MutableSet<Int> {
    val dirtyZIndices = mutableSetOf<Int>()

    for (trial in 1..1000) {
        val x = (abs(Random.nextLong()) % (2.0.pow(45) - 1)).toLong()
        val y = (abs(Random.nextLong()) % (2.0.pow(45) - 1)).toLong()

        val wireVals = numToWireVals(x, y)
        compute(gates, wireVals, swaps)
        val keys = wireVals.keys.filter { it.startsWith("z") }.sorted()
        val zActual = keys.map { wireVals[it]!! }.joinToString("")
        val zExpected = (x + y).toString(2).reversed()
        for (i in 0..45) {
            if (zActual.length > i) {
                if (zExpected.length <= i) {
                    if (zActual[i] == '1') dirtyZIndices.add(i)
                } else if (zActual[i] != zExpected[i]) {
                    dirtyZIndices.add(i)
                }
            }
        }
    }
    return dirtyZIndices
}

fun findWiresFeedingDirtyZs(gates: List<Gate>, dirtyZIndices: Collection<Int>): Set<Gate> {
    val dirtyGates = mutableSetOf<Gate>()
    for (i in dirtyZIndices) {
        val iPad = if (i < 10) "0" + i.toString() else i.toString()
        val zWire = "z" + iPad

        val toVisit = mutableSetOf(zWire)
        while(toVisit.isNotEmpty()) {
            val visiting = toVisit.first()
            toVisit.remove(visiting)
            gates.filter{it.out == visiting }.forEach{ toVisit.add(it.in1); toVisit.add(it.in2); dirtyGates.add(it) }
        }

    }
    return dirtyGates
}

fun numToWireVals(x: Long, y: Long): MutableMap<String, Int> {
    val vals = mutableMapOf<String, Int>()
    val xs = x.toString(2).reversed()
    val ys = y.toString(2).reversed()
    if (xs.length > 45 || ys.length > 45) throw Error()
    for (i in 0..44) {
        val iPad = if (i < 10) "0" + i.toString() else i.toString()
        if (i >= xs.length) vals["x" + iPad] = 0 else vals["x" + iPad] = xs[i].toString().toInt()
        if (i >= ys.length) vals["y" + iPad] = 0 else vals["y" + iPad] = ys[i].toString().toInt()
    }

    return vals
}
