package d6

import utils.*

fun main() {
    val list = readList("/d6/d6p1")
    var fish = list[0].split(",").map{it}.groupBy { it.toInt() }.mapValues { it.value.size.toLong() }.toMutableMap()

    for (i in 0..255) {
        val newFish = mutableMapOf<Int, Long>()
        for (j in 0..8) {
            val fishAtJ = fish.getOrDefault(j, 0)
            if (j == 0) {
                newFish.put(8, fishAtJ)
                newFish.put(6, fishAtJ)
            } else {
                newFish.put(j-1, newFish.getOrDefault(j-1, 0) + fishAtJ)
            }
        }
        fish = newFish
    }

    println(fish.values.sum())


}

