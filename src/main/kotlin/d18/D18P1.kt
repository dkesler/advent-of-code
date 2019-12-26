package d18

import java.io.File

fun main() {
    val tilesByYX = File("src/main/resources/d18/d18p2").readLines()
        .mapIndexed { y, line -> line.mapIndexed{ x, tile -> Triple(x, y, tile)  } }
        .flatten()
        .groupBy{it.second }
        .mapValues { it.value.map{ Pair(it.first, it.third)}.toMap() }

    val cave: List<CaveTile2> = tilesByYX.flatMap { y -> y.value.map { x -> toCaveTile2(x.key, y.key, tilesByYX, x.value)}}

    println(cave)

    val start = cave.filter { it.type == '@' }.toSet()

    val keyTilesByKey = cave.filter { isKeyTile(it.type) }.map{ Pair(it.type, it) }.toMap()

    println(keyTilesByKey)

    val before = System.currentTimeMillis()
    println(Solver().navigate(start, keyTilesByKey, setOf(), 0, Int.MAX_VALUE))
    println("time: " + (System.currentTimeMillis() - before))
}

fun isKeyTile(type: Char): Boolean {
    return type.isLetter() && type.isLowerCase()
}

fun isDoorTile(type: Char): Boolean {
    return type.isLetter() && type.isUpperCase()
}

fun toCaveTile2(x: Int, y: Int, tilesByYX: Map<Int, Map<Int, Char>>, value: Char): CaveTile2 {

    fun findReachableKeys(): List<Edge> {
        val queue = mutableListOf(Triple(Pair(x, y), 0, setOf<Char>()))
        val visited = mutableSetOf(Pair(x, y))
        val reachableKeys = mutableListOf<Edge>()

        fun shouldExplore(newX: Int, newY: Int): Boolean {
            return !visited.contains(Pair(newX, newY))
                    && tilesByYX.containsKey(newY)
                    && tilesByYX.getValue(newY).containsKey(newX)
                    && tilesByYX.getValue(newY).getValue(newX) != '#'
        }

        while(queue.isNotEmpty()) {
            val next = queue.get(0)
            queue.removeAt(0)

            val tile = tilesByYX.getValue(next.first.second).getValue(next.first.first)
            if (isKeyTile(tile)  && tile != value) {
                reachableKeys.add(Edge(tile, next.second, next.third))
            }

            val keysRequired = next.third.toMutableSet()
            if (isDoorTile(tile)) {
                keysRequired.add(tile.toLowerCase())
            }

            val thisX = next.first.first
            val thisY = next.first.second

            if (shouldExplore(thisX+1, thisY)) {
                queue.add(Triple(Pair(thisX+1, thisY), next.second+1, keysRequired))
                visited.add(Pair(thisX+1, thisY))
            }
            if (shouldExplore(thisX-1, thisY)) {
                queue.add(Triple(Pair(thisX-1, thisY), next.second+1, keysRequired))
                visited.add(Pair(thisX-1, thisY))
            }
            if (shouldExplore(thisX, thisY+1)) {
                queue.add(Triple(Pair(thisX, thisY+1), next.second+1, keysRequired))
                visited.add(Pair(thisX, thisY+1))
            }
            if (shouldExplore(thisX, thisY-1)) {
                queue.add(Triple(Pair(thisX, thisY-1), next.second+1, keysRequired))
                visited.add(Pair(thisX, thisY-1))
            }
        }

        return reachableKeys;
    }

    return CaveTile2(findReachableKeys(), value)
}

data class Edge(val key: Char, val distance: Int, val keysRequired: Set<Char>) {
    fun hasAllkeys(keys: Set<Char>): Boolean {
        return keys.containsAll(keysRequired)
    }

    fun alreadyAcquired(keys: Set<Char>): Boolean {
        return keys.contains(key)
    }
}

data class Solver(val memoTable: MutableMap<Pair<Set<CaveTile2>, Set<Char>>, Int> = mutableMapOf()) {
    fun navigate(cur: Set<CaveTile2>, keyTilesByKey: Map<Char, CaveTile2>, keys: Set<Char>, currentDistance: Int, bestDistance: Int): Pair<Boolean, Int> {


        if (memoTable.containsKey(Pair(cur, keys))) {
            val memoValue = memoTable.getValue(Pair(cur, keys))
            if (memoValue + currentDistance < bestDistance) {
                println("new best distance found: ${memoValue + currentDistance}")
                return Pair(true, currentDistance + memoValue)
            } else {
                return Pair(false, bestDistance)
            }
        }


        val reachableKeys = cur.flatMap{
            it.otherKeys.filter{ !it.alreadyAcquired(keys) }.filter { it.hasAllkeys(keys) }.map{ e -> Pair(it, e) }
        }.sortedBy { it.second.distance }

        //we've found all keys
        if (reachableKeys.isEmpty()) {
            memoTable.put(Pair(cur, keys), 0)
            if (currentDistance < bestDistance) {
                println("new best distance found: $currentDistance")
            }
            return Pair(true, currentDistance)
        }

        if (currentDistance + reachableKeys[0].second.distance > bestDistance) {
            return Pair(false, bestDistance)
        }

        //choose closest key and find total distance
        var bestSubdistance: Int? = null

        //try remaining keys, bailing early if best distance < current distance
        for (key in reachableKeys) {
            //if moving to the next key goes over best distance, ignore it
            if (currentDistance + key.second.distance < bestDistance) {
                //find the distance necessary to find all remaining keys

                val subdistance = navigate(cur - key.first + keyTilesByKey.getValue(key.second.key), keyTilesByKey, keys + key.second.key, currentDistance + key.second.distance, bestSubdistance ?: bestDistance)
                //we have found all keys at a better distance than bestDistance or any subdistance
                if (subdistance.first && subdistance.second < bestDistance && (bestSubdistance == null || subdistance.second < bestSubdistance) ) {
                    bestSubdistance = subdistance.second
                }
            }
        }

        if (bestSubdistance != null) {
            memoTable.put(Pair(cur, keys), bestSubdistance - currentDistance)
            return Pair(true, bestSubdistance)
        } else {
            return Pair(false, bestDistance)
        }
    }
}

data class CaveTile2(val otherKeys: List<Edge>, val type: Char, var memoTable: MutableMap<Set<Char>, Int> = mutableMapOf())
