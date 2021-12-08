package d8

import utils.*

fun main() {
    val list = readList("/d8/d8p1").map{it.split("|")}.map{
        Pair(
            it[0].trim().split(" ").map{ it.trim().split("").filter{it != ""}.toSet()},
            it[1].trim().split(" ").map{ it.trim().split("").filter{it != ""}.toSet()}
        )
    }

    val outputs = list.map{getOutput(it.first, it.second)}

    println(outputs.sum())
}

fun getOutput(signals: List<Set<String>>, outputs: List<Set<String>>): Int {
    val oneSignal = signals.filter{it.size == 2}[0]
    val fourSignal = signals.filter{it.size == 4}[0]
    val sevenSignal = signals.filter{it.size == 3}[0]
    val eightSignal = signals.filter{it.size == 7}[0]

    val signalCounts = signals.flatten().groupBy { it }.mapValues{ it.value.size}

    val aSig = signalCounts.filter{ it.value == 8}.filter{ !oneSignal.contains(it.key) } .keys.first()
    val bSig = signalCounts.filter{ it.value == 6}.keys.first()
    val cSig = signalCounts.filter{ it.value == 8}.filter{ oneSignal.contains(it.key) } .keys.first()
    val dSig = signalCounts.filter{ it.value == 7}.filter{ fourSignal.contains(it.key) } .keys.first()
    val eSig = signalCounts.filter{ it.value == 4}.keys.first()
    val fSig = signalCounts.filter{ it.value == 9}.keys.first()
    val gSig = signalCounts.filter{ it.value == 7}.filter{ !fourSignal.contains(it.key) } .keys.first()

    val signalMap = mutableMapOf(
        Pair(oneSignal, 1),
        Pair(fourSignal, 4),
        Pair(sevenSignal, 7),
        Pair(eightSignal, 8),
        Pair(setOf(aSig, cSig, dSig, eSig, gSig) , 2),
        Pair(setOf(aSig, cSig, dSig, fSig, gSig), 3),
        Pair(setOf(aSig, bSig, dSig, fSig, gSig), 5),
        Pair(setOf(aSig, bSig, dSig, eSig, fSig, gSig), 6),
        Pair(setOf(aSig, bSig, cSig, dSig, fSig, gSig), 9),
    )

    val mapped = outputs.map { signalMap.getOrDefault(it, 0) }

    return mapped[0] * 1000 + mapped[1] * 100 + mapped[2] * 10 + mapped[3]
}


