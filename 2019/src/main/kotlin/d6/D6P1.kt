package d6

import java.io.File

fun main() {
    val orbits = File("2019/src/main/resources/d6/d6p1").readLines()
        .map(::toOrbitPair)
        .fold(
            mapOf(),
            ::accMap
        )

    println(orbits["COM"]?.numOrbits())
}

fun accMap(acc: Map<String, Tree>, orbit: Pair<String, String>): Map<String, Tree> {
    var map = acc
    if (!map.containsKey(orbit.first)) {
        map = map.plus(Pair(orbit.first, Tree(orbit.first)))
    }
    if (!map.containsKey(orbit.second)) {
        map = map.plus(Pair(orbit.second, Tree(orbit.second)))
    }

    map[orbit.first]?.addNode(map.getValue(orbit.second))

    return map
}

fun toOrbitPair(line: String): Pair<String, String> {
    val split = line.trim().split(")")
    return Pair(split[0], split[1])
}

data class Tree(val thisNode: String, val nodes: MutableList<Tree>, var parent: String) {
    constructor(thisNode: String): this(thisNode, mutableListOf<Tree>(), "")

    fun addNode(node: Tree) {
        nodes.add(node)
        node.parent = thisNode
    }

    fun numChildren(): Int {
        return nodes.map{ it.numChildren()}.sum() + nodes.size
    }

    fun numOrbits(): Int {
        return nodes.map{ it.numOrbits() }.sum() + numChildren();
    }
}
