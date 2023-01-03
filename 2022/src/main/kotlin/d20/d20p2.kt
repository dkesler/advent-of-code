package d20

import utils.readLongList
//8688
fun main() {
    val values = readLongList("/d20.txt")

    val reordered = mutableListOf<Pair<Long, Int>>()
    reordered.addAll(values.mapIndexed{ i, v -> Pair(v * 811589153, i)})

    for (iter in (1..10)) {
        for (idx in values.indices) {
            val value = values[idx]
            val curIdx = reordered.indexOf(Pair(value * 811589153, idx))
            reordered.removeAt(curIdx)

            val eVal = value * 811589153 % reordered.size

            var newIdx = (curIdx + eVal) % reordered.size
            if (newIdx < 0) newIdx += reordered.size


            reordered.add(newIdx.toInt(), Pair(value * 811589153, idx))
        }
    }

    val iOf0 = reordered.indexOf(Pair(0, values.indexOf(0)))

    println(
            reordered[(iOf0 + 1000) % reordered.size].first + reordered[(iOf0 + 2000) % reordered.size].first + reordered[(iOf0 + 3000) % reordered.size].first
    )



}