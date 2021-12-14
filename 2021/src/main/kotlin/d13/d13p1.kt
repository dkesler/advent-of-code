package d13

import utils.*

fun main() {
    val points = readList("/d13/d13p1").map{it.split(",")}.map{Pair(it[0].toInt(), it[1].toInt())}.toSet()
    val instr = readList("/d13/d13p2").map{it.split(" ")[2]}.map{it.split("=")}
        .map{Pair(it[0], it[1].toInt())}


    val folded = instr.fold(points, ::doFold)

    val maxX = folded.map{it.first}.maxOrNull() ?: 0
    val maxY = folded.map{it.second}.maxOrNull() ?: 0

    for (x in maxX downTo 0) {
        for (y in 0..maxY) {
            if (folded.contains(Pair(x, y))) {
                print("#")
            }else {
                print(".")
            }
        }
        println("")
    }
}

fun doFold(points: Set<Pair<Int, Int>>, instr: Pair<String, Int>): Set<Pair<Int, Int>> {
    if (instr.first == "x") {
        return points.map{
            if (it.first < instr.second) {
                it
            } else {
                Pair(instr.second - (it.first-instr.second), it.second)
            }
        }.toSet()
    } else {
        return points.map{
            if (it.second < instr.second) {
                it
            } else {
                Pair(it.first, instr.second - (it.second-instr.second))
            }
        }.toSet()
    }
}
