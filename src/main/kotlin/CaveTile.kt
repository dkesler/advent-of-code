fun toCaveTile(x: Int, y: Int, tilesByYX: Map<Int, Map<Int, Char>>, value: Char): CaveTile {

    val neighbors = listOf(
        Pair(x, y-1),
        Pair(x, y+1),
        Pair(x-1, y),
        Pair(x+1, y)
    ).filter{ tilesByYX.containsKey(it.second) && tilesByYX.getValue(it.second).containsKey(it.first) && tilesByYX.getValue(it.second).getValue(it.first) != '#' }

    return CaveTile(x, y, neighbors, value)
}

data class CaveTile(val x: Int, val y: Int, val neighbors: List<Pair<Int, Int>>, val type: Char, var memoTable: MutableMap<Set<Char>, Int> = mutableMapOf()) {
    fun navigate(tiles: Map<Int, Map<Int, CaveTile>>, keys: Set<Char>, currentDistance: Int, bestDistance: Int): Pair<Boolean, Int> {

        if (memoTable.containsKey(keys)) {
//            println("memo table hit: $keys")
            if (memoTable.getValue(keys) + currentDistance < bestDistance) {
                println("new best distance found: ${memoTable.getValue(keys) + currentDistance}")
                return Pair(true, currentDistance + memoTable.getValue(keys))
            } else {
                return Pair(false, bestDistance)
            }
        }

        //find all reachable keys + distances to those keys
        val reachableKeys = findReachableKeys(x, y, tiles, keys).sortedBy { it.second }

        //we've found all keys
        if (reachableKeys.isEmpty()) {
            memoTable.put(keys, 0)
            if (currentDistance < bestDistance) {
                println("new best distance found: $currentDistance")
            }
            return Pair(true, currentDistance)
        }

        if (currentDistance + reachableKeys[0].second > bestDistance) {
            return Pair(false, bestDistance)
        }

        //choose closest key and find total distance
        var bestKey: Pair<CaveTile, Int>? = null
        var bestSubdistance: Int? = null

        //try remaining keys, bailing early if best distance < current distance
        for (key in reachableKeys) {
            //even moving to the next key goes over gest distance
            if (currentDistance + key.second < bestDistance) {
                //find the distance necessary to find all remaining keys
                val subdistance = key.first.navigate(tiles, keys + key.first.type, currentDistance + key.second, bestSubdistance ?: bestDistance)
                //we have found all keys at a better distance than bestDistance or any subdistance
                if (subdistance.first && subdistance.second < bestDistance && (bestSubdistance == null || subdistance.second < bestSubdistance) ) {
                    bestKey = key
                    bestSubdistance = subdistance.second
                }
            }
        }

        if (bestSubdistance != null) {
            memoTable.put(keys, bestSubdistance - currentDistance)
            return Pair(true, bestSubdistance)
        } else {
            return Pair(false, bestDistance)
        }
    }

    private fun findReachableKeys(x: Int, y: Int, tiles: Map<Int, Map<Int, CaveTile>>, keys: Set<Char>): List<Pair<CaveTile, Int>> {
        val queue = mutableListOf(Triple(x, y, 0))
        val visited = mutableSetOf(Pair(x, y))
        val reachableKeys = mutableListOf<Pair<CaveTile, Int>>()

        while(queue.isNotEmpty()) {
            val next = queue.get(0)
            val tile = tiles.getValue(next.second).getValue(next.first)
            queue.removeAt(0)

            if (isKeyTile(tile.type) && !keys.contains(tile.type)) {
                reachableKeys.add(Pair(tile, next.third))
            } else {
                tile.neighbors.forEach {
                    checkDirection(visited, tiles, keys, queue, it.first, it.second, next.third)
                }
            }
        }

        return reachableKeys;
    }

    private fun isKeyTile(type: Char): Boolean {
        return type.isLetter() && type.isLowerCase()
    }

    private fun checkDirection(
        visited: MutableSet<Pair<Int, Int>>,
        tiles: Map<Int, Map<Int, CaveTile>>,
        keys: Set<Char>,
        queue: MutableList<Triple<Int, Int, Int>>,
        x: Int,
        y: Int,
        distance: Int
    ) {
        if (!visited.contains(Pair(x, y))) {
            if (isReachable(keys, tiles.getValue(y).getValue(x))) {
                queue.add(Triple(x, y, distance + 1))
                visited.add(Pair(x, y))
            }
        }
    }

    //a tile is reachable if it's empty, a key, or a door that we have a key to
    private fun isReachable(keys: Set<Char>, tile: CaveTile): Boolean {
        if (tile.type == '.' || tile.type == '@' || isKeyTile(tile.type)) {
            return true
        }

        if (keys.contains(tile.type.toLowerCase())) {
            return true
        }

        return false
    }
}
