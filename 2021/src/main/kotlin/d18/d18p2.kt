package d18

import utils.*

fun main() {
    val list = readList("/d18/d18p1").map{toSnailfishNumber(it)}

    val maxMagnitude = list.map{ i1 -> list.map{ i2 -> Pair(i1, i2)}}
        .flatten()
        .filter{ it.first != it.second }
        .map{ it.first.add(it.second)}
        .map{it.magnitude()}
        .maxOrNull()!!

    println(maxMagnitude)



}
