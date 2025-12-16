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


    println(pathsToOutFromIncluding(connections, setOf(), "svr"))
}

val memoTable2 = mutableMapOf<Pair<String, Set<String>>, Long>()
fun pathsToOutFromIncluding(connections: Map<String, List<String>>, path: Set<String>, start: String): Long {
    val memoKey = Pair(start, path)
    if (memoKey in memoTable2) return memoTable2[memoKey]!!
    if (start == "out" && path.contains("fft") && path.contains("dac")) return 1
    if (start == "out") return 0

    var sum = 0L
    for (target in connections[start]!!) {
        val newPath = if(target == "fft" || target == "dac") path + target else path
        sum += pathsToOutFromIncluding(connections, newPath, target)
    }
    memoTable2[memoKey] = sum
    return sum
}
