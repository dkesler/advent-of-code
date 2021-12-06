package d6

import utils.*

fun main() {
    val list = readList("/d6/d6p1")
    var fish = list[0].split(",").map{it.toInt()}
    for (i in 0..79) {
        fish = fish.map{
            if (it == 0) {
                listOf(6, 8)
            } else {
                listOf(it-1)
            }
        }.flatten()
    }
    println(fish.size)


}
