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

    var largestClique = setOf<String>()

    for (computer in conMap.keys) {
        val clique = findLargestClique(conMap, computer, largestClique.size+1)
        if (clique.size > largestClique.size) largestClique = clique
    }

    println(largestClique.toList().sorted().joinToString(","))

}

fun findLargestClique(conMap: MutableMap<String, MutableList<String>>, computer: String, minCliqueSize: Int): Set<String> {
    val connections = conMap[computer]!!
    for (i in connections.size downTo minCliqueSize-1) {
        for (perm in perms(connections.toSet(), i)) {
            if (perm.all{neighbor -> conMap[neighbor]!!.containsAll(perm.filter{ it != neighbor})}) return perm + computer
        }
    }
    return setOf()
}

fun perms(connections: Set<String>, size: Int): Set<Set<String>> {
    if (size == connections.size) return setOf(connections)

    return connections.flatMap{perms(connections - it, size)}.toSet()
}
