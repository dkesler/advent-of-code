package d18

import utils.readList

fun main() {
    val lines = readList("/d18test.txt")
    val cubes = lines.map{ it.split(",")}.map{Triple(it[0].toInt(), it[1].toInt(), it[2].toInt())}.toSet()

    val sa = cubes.map {
        val neighbors = setOf(
                Triple(it.first-1, it.second, it.third),
                Triple(it.first+1, it.second, it.third),
                Triple(it.first, it.second-1, it.third),
                Triple(it.first, it.second+1, it.third),
                Triple(it.first, it.second, it.third-1),
                Triple(it.first, it.second, it.third+1)
        ).filter{ it in cubes}
        6 - neighbors.count()
    }.sum()


    println(sa)

}