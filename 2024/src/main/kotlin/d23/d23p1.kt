package d23

import utils.*

fun main() {
    val lines = readList("/d23.txt")
    val conMap = mutableMapOf<String, MutableList<String>>()
    for (line in lines) {
        val split = line.split("-")
        if (split[0] !in conMap.keys) conMap[split[0]] = mutableListOf<String>()
        if (split[1] !in conMap.keys) conMap[split[1]] = mutableListOf<String>()
        conMap[split[0]]!!.add(split[1])
        conMap[split[1]]!!.add(split[0])
    }
    val trips = mutableSetOf<List<String>>()
    for (computer in conMap.keys.filter{it.startsWith("t")}) {
        for (i1 in conMap[computer]!!.indices) {
            for (i2 in i1+1 until conMap[computer]!!.size) {
                val c1 = conMap[computer]!![i1]
                val c2 = conMap[computer]!![i2]
                if (c1 in conMap[c2]!!) {
                    trips.add(listOf(computer, c1, c2).sorted())
                }
            }
        }
    }

    println(trips.size)



}