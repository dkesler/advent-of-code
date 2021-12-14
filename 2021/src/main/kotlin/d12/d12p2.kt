package d12
import utils.*

fun main() {
    val list = readList("/d12/d12p1")
    val split = list.map{it.split("-")}

    val adjMap = split.flatten().toSet().map{
        val adj = findAdjacent(it, split)
        Pair(it, adj)
    }.toMap()

    val paths = findPaths2(adjMap, "start", "end")
    println(paths);
    println(paths.size)

}

fun findPaths2(adjMap: Map<String, List<String>>, start: String, end: String): Set<List<String>> {
    val paths = mutableSetOf<List<String>>()
    fun isUpper(node: String): Boolean {
        return node.toUpperCase() == node
    }

    fun findPathsIter(visited: List<String>, current: String, doubleVisit: Boolean) {
        if (current == end) {
            paths.add(visited)
        } else {
            val toVisit = adjMap.getOrDefault(current, listOf()).filter{ isUpper(it) || !visited.contains(it) || (!doubleVisit && it != start && it != end)}
            toVisit.forEach{
                val isThisDoubleVisit = !isUpper(it) && visited.contains(it)
                findPathsIter(visited+it, it, isThisDoubleVisit || doubleVisit)
            }
        }
    }
    findPathsIter(listOf(start), start, false)
    return paths
}



