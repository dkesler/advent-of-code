package d12

import utils.*

fun main() {
    val list = readList("/d12/d12p1")
    val split = list.map{it.split("-")}

    val adjMap = split.flatten().toSet().map{
        val adj = findAdjacent(it, split)
        Pair(it, adj)
    }.toMap()

    val paths = findPaths(adjMap, "start", "end")
    println(paths);
    println(paths.size)

}

fun findPaths(adjMap: Map<String, List<String>>, start: String, end: String): Set<List<String>> {
    val paths = mutableSetOf<List<String>>()
    fun isUpper(node: String): Boolean {
        return node.toUpperCase() == node
    }

    fun findPathsIter(visited: List<String>, current: String) {
        if (current == end) {
            paths.add(visited)
        } else {
            val toVisit = adjMap.getOrDefault(current, listOf()).filter{ isUpper(it) || !visited.contains(it)}
            toVisit.forEach{
                findPathsIter(visited+it, it)
            }
        }
    }
    findPathsIter(listOf(start), start)
    return paths
}

fun findAdjacent(node: String, joins: List<List<String>>): List<String> {
    return joins.filter{it.contains(node)}.flatten().filter{it != node}
}



