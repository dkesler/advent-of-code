package d24

fun toInstructions(line: String): List<String> {
    var idx = 0
    val res = mutableListOf<String>()
    while (idx < line.length) {
        if (line[idx] == 'e' || line[idx] == 'w') {
            res.add(line.substring(idx, idx+1))
            idx++
        } else {
            res.add(line.substring(idx, idx+2))
            idx += 2
        }
    }

    return res
}


fun toXY(moves: List<String>): Pair<Int, Int> {
    var x = 0
    var y = 0
    for (move in moves) {
        if (move == "e") {
            x += 2
        } else if (move == "w") {
            x -= 2
        } else if (move == "se") {
            x += 1
            y -= 1
        } else if (move == "sw") {
            x -= 1
            y -= 1
        } else if (move == "ne") {
            y += 1
            x += 1
        } else if (move == "nw") {
            y += 1
            x -= 1
        }
    }

    return Pair(x, y)
}

fun adjacentTiles(tile: Pair<Int, Int>): List<Pair<Int, Int>> {
    return listOf(
        Pair(tile.first+2, tile.second),
        Pair(tile.first-2, tile.second),
        Pair(tile.first+1, tile.second+1),
        Pair(tile.first+1, tile.second-1),
        Pair(tile.first-1, tile.second+1),
        Pair(tile.first-1, tile.second-1)
    )
}

fun shouldFlipWhite(tile: Pair<Int, Int>, blackTiles: Set<Pair<Int, Int>>): Boolean {
    val adjBlack = adjacentTiles(tile).filter{blackTiles.contains(it)}

    return adjBlack.size == 0 || adjBlack.size > 2
}

fun shouldFlipBlack(tile: Pair<Int, Int>, blackTiles: Set<Pair<Int, Int>>): Boolean {
    val adjBlack = adjacentTiles(tile).filter{blackTiles.contains(it)}

    return adjBlack.size == 2
}


fun tick(blackTiles: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    val newBlackTiles = blackTiles.toMutableSet()

    for (tile in blackTiles) {
        if (shouldFlipWhite(tile, blackTiles)) {
         newBlackTiles.remove(tile)
        }

        for (adjTile in adjacentTiles(tile)) {
            if (!blackTiles.contains(adjTile)) {
                if (shouldFlipBlack(adjTile, blackTiles)) {
                    newBlackTiles.add(adjTile)
                }
            }
        }
    }

    return newBlackTiles
}


fun main() {
    val content = Class::class.java.getResource("/d24/d24p1").readText()
    val list = content.split("\n").filter{it != ""}.map(::toInstructions)

    val blackTiles = list.map(::toXY).groupBy { it }.mapValues { it.value.size }
        .filter { it.value % 2 == 1 }

    val count = blackTiles.count()

    println(count)

    var blackTiles2 = blackTiles.keys

    for (i in 0 until 100) {
        blackTiles2 = tick(blackTiles2)
        println(blackTiles2.size)
    }

    println(blackTiles2.size)


}
