package d7

import utils.readList

fun main() {
    val list = readList("/d7/d7p1test")[0].split(",").map{it.toInt()}

    val pos = Math.round(list.sum().toDouble() / list.size)

    println(pos)
    println(list.map{ Math.abs(it - pos) }.sum())

    val min = list.minOrNull() ?: 0
    val max = list.maxOrNull() ?: 0

    println((min..max).map{ pos -> list.map{ Math.abs(it-pos) * (Math.abs(it-pos)+1) / 2}.sum()}.min())

}
