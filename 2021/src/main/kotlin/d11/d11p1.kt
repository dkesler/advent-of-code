package d11
import utils.*

fun main() {
    var g = readLongGrid("/d11/d11p1")
    var totalFlashed = 0
    for(step in 1..100) {
        println(g)
        g = tick(g)
        totalFlashed += g.flatten().count { it == 0L }
    }
    println(totalFlashed)
}

fun tick(g: List<List<Long>>) : List<List<Long>> {
    val gm = g.map{it.map{Pair(it+1, false)}.toMutableList()}.toMutableList()
    while(flash(gm)){
//
    }
    return gm.map{it.map{ if(it.first >= 10L) 0L else it.first}.toList()}.toList()
}

fun flash(gm: MutableList<MutableList<Pair<Long, Boolean>>>): Boolean {
    val flashing = mutableListOf<Pair<Int, Int>>()
    for (x in 0..gm.size-1) {
        for (y in 0..gm[0].size-1) {
            if (gm[x][y].first >= 10 && !gm[x][y].second) {
                flashing.add(Pair(x, y))
                gm[x][y] = Pair(gm[x][y].first, true)
            }
        }
    }

    for (f in flashing) {
        genAdjacent(f).filter{ it.first >= 0 && it.second >= 0 && it.first < gm.size && it.second < gm[0].size }
            .forEach{
                val point = gm[it.first][it.second]
                gm[it.first][it.second] = Pair(point.first+1, point.second)
            }
    }

    return flashing.isNotEmpty()
}

fun genAdjacent(point: Pair<Int, Int>): List<Pair<Int, Int>> {
    return listOf(
        Pair(point.first-1, point.second-1),
        Pair(point.first-1, point.second),
        Pair(point.first-1, point.second+1),
        Pair(point.first, point.second-1),
        Pair(point.first, point.second),
        Pair(point.first, point.second+1),
        Pair(point.first+1, point.second-1),
        Pair(point.first+1, point.second),
        Pair(point.first+1, point.second+1),
    )
}


