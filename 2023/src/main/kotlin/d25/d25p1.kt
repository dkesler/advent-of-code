package d25

import org.apache.commons.rng.UniformRandomProvider
import org.apache.commons.rng.sampling.DiscreteProbabilityCollectionSampler
import utils.*
import kotlin.random.Random

fun findMinCut(cons: Map<String, Map<String, Int>>): Map<String, Map<String, Int>> {
    var attempts = 0
    while(true) {
        val contracted = contract2(cons, 2)
        if (contracted.values.all{ it.values.all{ it == 3 }}) return contracted
        attempts++
        if (attempts % 1000 == 0) println("Attempt ${attempts}")

    }
}

fun findMergeNodes(cons: Map<String, Map<String, Int>>): Pair<String, String> {
    val dist = DiscreteProbabilityCollectionSampler(
            UniformRandomProvider { Random.nextLong() },
            cons.mapValues{ it.value.values.sum().toDouble() }
    )
    val n1 = dist.sample()
    val dist2 = DiscreteProbabilityCollectionSampler(
            UniformRandomProvider { Random.nextLong() },
            cons[n1]!!.mapValues{ it.value.toDouble() }
    )
    val n2 = dist2.sample()
    return Pair(n1, n2)
}

fun contract2(cons: Map<String, Map<String, Int>>, targetNodes: Int): Map<String, Map<String, Int>> {
    val mCons = cons.mapValues{ it.value.toMutableMap() }.toMutableMap()

    while(mCons.keys.size > targetNodes) {
        val (n1, n2) = findMergeNodes(mCons)
        merge(n1, n2, mCons)

    }
    return mCons

}

private fun merge(n1: String, n2: String, mCons: MutableMap<String, MutableMap<String, Int>>) {
    val merged = n1 + n2

    val n1Cons = mCons[n1]!!
    val n2Cons = mCons[n2]!!

    mCons[merged] = (n1Cons.keys + n2Cons.keys)
            .filter { it != n1 && it != n2 }
            .map { Pair(it, n1Cons.getOrDefault(it, 0) + n2Cons.getOrDefault(it, 0)) }
            .toMap().toMutableMap()

    for (c in mCons[merged]!!) {
        val cNode = c.key
        val consTo1 = mCons[cNode]?.getOrDefault(n1, 0) ?: 0
        val consTo2 = mCons[cNode]?.getOrDefault(n2, 0) ?: 0

        mCons[cNode]?.remove(n1)
        mCons[cNode]?.remove(n2)
        mCons[cNode]?.let { it[merged] = consTo1 + consTo2 }
    }
    mCons.remove(n1)
    mCons.remove(n2)
}


fun main() {
    val lines = readList("/d25.txt")

    val connections = mutableMapOf<String, Map<String, Int>>()
    lines.forEach {
        val s = it.split(":")
        val r = s[1].split(" ").map{ it.trim() }.filter{ it != "" }

        val sCon = connections.getOrDefault(s[0], mapOf())
        connections[s[0]] = r.map{ Pair(it, 1) }.toMap() + sCon

        r.forEach {
            val con = connections.getOrDefault(it, mapOf())
            connections[it] = con + Pair(s[0], 1)
        }
    }

    val partitioned = findMinCut(connections)

    println(
            partitioned.keys.map{ it.length / 3 }.fold(1) { a, b -> a * b }
    )



}