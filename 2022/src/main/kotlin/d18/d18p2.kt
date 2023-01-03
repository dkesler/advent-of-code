package d18

import utils.readList

fun main() {
    val lines = readList("/d18.txt")
    val cubes = lines.map{ it.split(",")}.map{Triple(it[0].toInt(), it[1].toInt(), it[2].toInt())}.toSet()

    val top = cubes.maxOf { it.first }
    val topCube = cubes.filter{it.first == top}.take(1)[0]
    val toVisit = mutableSetOf( Triple(topCube.first+1, topCube.second, topCube.third) )
    val visited = mutableSetOf<Triple<Int, Int, Int>>()
    var sa = 0

    while(toVisit.isNotEmpty()) {
        val visiting = toVisit.first()
        toVisit.remove(visiting)
        visited.add(visiting)

        sa += orthogonalNeighbors(visiting).count{ it in cubes}

        val neighbors = orthogonalNeighbors(visiting)
                .filter{ it !in visited && it !in toVisit && it !in cubes}
                .filter { neighbor ->
                    val neighborNeighbor = neighbors(neighbor)
                    neighborNeighbor.any { it in cubes }
                }

        toVisit.addAll(neighbors)
    }

    println(sa)
}

fun orthogonalNeighbors(node: Triple<Int, Int, Int>): Set<Triple<Int, Int, Int>> {
    return setOf(
            Triple(node.first-1, node.second, node.third),
            Triple(node.first+1, node.second, node.third),
            Triple(node.first, node.second-1, node.third),
            Triple(node.first, node.second+1, node.third),
            Triple(node.first, node.second, node.third-1),
            Triple(node.first, node.second, node.third+1)
    )
}

fun neighbors(node: Triple<Int, Int, Int>): Set<Triple<Int, Int, Int>> {
    val neighbors = mutableSetOf<Triple<Int, Int, Int>>()
    for (x in (-1..1)) {
        for (y in (-1..1)) {
            for (z in (-1..1)) {
                neighbors.add(Triple(node.first+x, node.second+y, node.third+z))
            }
        }
    }
    return neighbors
}
