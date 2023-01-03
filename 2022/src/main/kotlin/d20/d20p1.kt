package d20

import utils.readLongList
//8688
fun main() {
    val values = readLongList("/d20.txt")

    val reordered = mutableListOf<Pair<Long, Boolean>>()
    reordered.addAll(values.map{ Pair(it, false)})

    for (value in values) {
        val curIdx = reordered.indexOf(Pair(value, false))
        reordered.removeAt(curIdx)

        val eVal = value % reordered.size

        var newIdx = (curIdx + eVal) % reordered.size
        if (newIdx < 0) newIdx += reordered.size


        reordered.add(newIdx.toInt(), Pair(value, true))
    }

    val iOf0 = reordered.indexOf(Pair(0, true))

    println(
            reordered[(iOf0 + 1000) % reordered.size].first + reordered[(iOf0 + 2000) % reordered.size].first + reordered[(iOf0 + 3000) % reordered.size].first
    )



}