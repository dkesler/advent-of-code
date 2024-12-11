package d11

import utils.*

fun main() {
    var stones = readList("/d11.txt")[0].split(" ").filter{ it.isNotBlank()}.map{ it.toLong() }

    for (i in 0 until 25) {
        val newStones = mutableListOf<Long>()

        for (stone in stones) {
            if (stone == 0L) newStones.add(1)
            else if (stone.toString().length % 2 == 0) {
                val stoneString = stone.toString()
                val l = stoneString.substring(0, stoneString.length/2)
                val r = stoneString.substring(stoneString.length/2, stoneString.length)
                newStones.add(l.toLong())
                newStones.add(r.toLong())
            } else {
                newStones.add(stone * 2024)
            }
        }
        stones = newStones.toList();
    }
    println(stones.size)
}