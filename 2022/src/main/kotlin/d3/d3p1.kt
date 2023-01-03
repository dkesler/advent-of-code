package d3

import utils.*

fun main() {
    val lines = readCharGrid("/d3.txt")

    val common = lines.map {
        it.subList(it.size / 2, it.size).toSet().intersect(it.subList(0, it.size / 2).toSet())
    }

    val x = common.map{it.first()}
            .map {
                if (it.isUpperCase())
                    it.toInt() - 'A'.toInt() + 27
                else
                    it.toInt() - 'a'.toInt() + 1
            }

    println(x.sum())

}