package d11

import utils.readList

fun main() {
    val lines = readList("/d11.txt")

    val connections = lines.map{
        val s = it.split(": ")
        val source = s[0]
        val ss = s[1].split(" ")
        Pair(source, ss)
    }.toMap()


    println(pathsToOutFrom(connections, "you"))
}

val memoTable = mutableMapOf<String, Long>()
fun pathsToOutFrom(connections: Map<String, List<String>>, start: String): Long {
    if (start in memoTable) return memoTable[start]!!
    if (start == "out") return 1

    var sum = 0L
    for (target in connections[start]!!) {
        sum += pathsToOutFrom(connections, target)
    }
    memoTable[start] = sum
    return sum
}
