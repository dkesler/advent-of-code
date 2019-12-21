package d20

import java.io.File

fun main() {
    val tiles = File("src/main/resources/d20p1-test2").readLines()
        .map{ it.split("").filter{it.isNotEmpty()} }

    //find labelled tile locations
    val labelledTiles = findLabelledTiles(tiles)
    val labelledTilesByMapLabel = labelledTiles.groupBy{ it.originalLabel }

    //find distances between all labelled tiles
    val edges: Map<String, List<Edge>> = findDistances(labelledTiles, tiles)


    //create nodes + edges
    val nodes = labelledTiles.map { lt ->
        Node(lt.label, lt.levelChange, edges.getValue(lt.label).plus(getTeleportEdge(lt, labelledTilesByMapLabel)))
    }

    println(nodes)

    val nodesByName = nodes.map{ Pair(it.name, it) }.toMap()

    val start = nodesByName.getValue("AAUP")
    val end = nodesByName.getValue("ZZUP")

    //findPath
    println(findPath(start, end, nodesByName, setOf("AAUP"), 0, Int.MAX_VALUE))
}

fun getTeleportEdge(tile: LabelledTile, labelledTilesByMapLabel: Map<String, List<LabelledTile>>): List<Edge> {
    return labelledTilesByMapLabel.getValue(tile.originalLabel)
        .filter{it.label != tile.label}
        .map { Edge(it.label, 1) }
}

fun findDistances(labelledTiles: List<LabelledTile>, tiles: List<List<String>>): Map<String, List<Edge>> {
    return labelledTiles.map{ Pair(it.label, findDistances(it, labelledTiles, tiles)) }.toMap()
}

fun findDistances(source: LabelledTile, labelledTiles: List<LabelledTile>, tiles: List<List<String>>): List<Edge> {
    val visited = mutableSetOf(Pair(source.x, source.y))
    val queue = mutableListOf(Triple(source.x, source.y, 0))
    val edges = mutableListOf<Edge>()

    val xyToLabel = labelledTiles.map{ Pair(Pair(it.x, it.y), it.label) }.toMap()

    while(queue.isNotEmpty()) {
        val head = queue.get(0)
        queue.removeAt(0)

        val neighbors = listOf(
            Triple(head.first+1, head.second, head.third+1),
            Triple(head.first-1, head.second, head.third+1),
            Triple(head.first, head.second+1, head.third+1),
            Triple(head.first, head.second-1, head.third+1)
        ).filter{ tiles[it.second][it.first] == "."}.filter{ !visited.contains(Pair(it.first, it.second)) }
        queue.addAll(neighbors)
        visited.addAll(neighbors.map{Pair(it.first, it.second)})

        if (xyToLabel.containsKey(Pair(head.first, head.second))) {
            val label = xyToLabel.getValue(Pair(head.first, head.second))
            if (label != source.label) {
                edges.add(Edge(label, head.third))
            }
        }
    }

    return edges.toList()
}

fun findLabelledTiles(tiles: List<List<String>>): List<LabelledTile> {
    fun isLabel(s: String): Boolean {
        return s[0].isLetter()
    }

    fun hasLabel(tile: Triple<Int, Int, String>): Boolean {
        return isLabel(tiles[tile.second][tile.first+1])
                || isLabel(tiles[tile.second][tile.first-1])
                || isLabel(tiles[tile.second+1][tile.first])
                || isLabel(tiles[tile.second-1][tile.first])
    }

    data class MapLabel(val label: String, val levelChange: Int)
    fun getMapLabel(tile: Triple<Int, Int, String>): MapLabel {
        if (isLabel(tiles[tile.second][tile.first+1])) {
            val label = tiles[tile.second][tile.first + 1] + tiles[tile.second][tile.first + 2]
            if (tile.first+3 >= tiles[tile.second].size) {
                return MapLabel(label, -1)
            } else {
                return MapLabel(label, 1)
            }
        } else if (isLabel(tiles[tile.second][tile.first-1])) {
            val label = tiles[tile.second][tile.first - 2] + tiles[tile.second][tile.first - 1]
            if (tile.first-3 < 0) {
                return MapLabel(label, -1)
            } else {
                return MapLabel(label, 1)
            }
        } else if (isLabel(tiles[tile.second+1][tile.first])) {
            val label = tiles[tile.second + 1][tile.first] + tiles[tile.second + 2][tile.first]
            if (tile.second+3 >= tiles.size) {
                return MapLabel(label, -1)
            } else {
                return MapLabel(label, 1)
            }
        } else {
            val label = tiles[tile.second - 2][tile.first] + tiles[tile.second - 1][tile.first]
            if (tile.second-3 < 0) {
                return MapLabel(label, -1)
            } else {
                return MapLabel(label, 1)
            }
        }
    }

    fun getLabel(tile: Triple<Int, Int, String>): String {
        val mapLabel = getMapLabel(tile)
        if (mapLabel.levelChange == 1) {
            return mapLabel.label + "DN"
        } else {
            return mapLabel.label + "UP"
        }
    }

    val emptyTiles = tiles.mapIndexed{
        y, line -> line.mapIndexed{
            x, tile ->Triple(x, y, tile)
        }
    }.flatten().filter { it.third == "." }

    return emptyTiles.filter(::hasLabel).map{
        val mapLabel = getMapLabel(it)
        LabelledTile(it.first, it.second, getLabel(it), mapLabel.label, mapLabel.levelChange)
    }
}


fun findPath(current: Node, target: Node, nodes: Map<String, Node>, visited: Set<String>, currentDistance: Int, bestDistance: Int): Pair<Boolean, Int> {
    if (current == target) {
        return Pair(true, currentDistance)
    }

    val candidiates = current.neighbors.filter{ !visited.contains(it.end) }.sortedBy { it.dist  }
    if (candidiates.isEmpty()) {
        return Pair(false, bestDistance)
    }

    var bestSubdistance: Int? = null

    for (candidate in candidiates) {
        if (currentDistance + candidate.dist < bestDistance) {
            val candidateNode = nodes.getValue(candidate.end)
            val subdistance = findPath(candidateNode, target, nodes, visited + current.name, currentDistance + candidate.dist, bestSubdistance ?: bestDistance)
            if (subdistance.first && subdistance.second < bestDistance && (bestSubdistance == null || subdistance.second < bestSubdistance)) {
                bestSubdistance = subdistance.second
            }
        }
    }

    if (bestSubdistance == null) {
        return Pair(false, bestDistance)
    } else {
        return Pair(true, bestSubdistance)
    }
}

data class Edge(val end: String, val dist: Int)
data class Node(val name: String, val levelChange: Int, var neighbors: List<Edge> = listOf())
data class LabelledTile(val x: Int, val y: Int, val label: String, val originalLabel: String, val levelChange: Int)

