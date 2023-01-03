package d10

import utils.readList

fun main() {
    val lines = readList("/d10.txt")
    var x = 1
    var cycle = 1
    var xVals = mutableMapOf(Pair(1, 0))

    for (line in lines) {
        if (line == "noop") {
            xVals[cycle] = x
            cycle++
        } else {
            val add = line.split(" ")[1].toInt()
            xVals[cycle] = x
            xVals[cycle+1] = x
            cycle += 2
            x += add
        }
    }

    println(
            xVals[20]!!*20 + xVals[60]!!*60 + xVals[100]!!*100 + xVals[140]!!*140 + xVals[180]!!*180 + xVals[220]!!*220
    )


    for (i in (1 .. 240)) {
        val pixel = i % 40-1

        if (pixel >= xVals[i]!!-1 && pixel <= xVals[i]!!+1) print("#") else print (".")

        if (i % 40 == 0) println("")
    }

}