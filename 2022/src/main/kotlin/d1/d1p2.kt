package d1

import utils.*

fun main() {
    val content = {}.javaClass.getResource("/d1.txt")!!.readText()
    val list = content.split("\n")

    val max = mutableListOf<Long>()
    var tot = 0L
    for (x in list) {
        if (x == "") {
            if (max.size < 3 || tot > max[0]) {
                if (max.size == 3) {
                    max.removeAt(0)
                }
                max.add(tot)
                max.sort()
            }
            tot = 0
        } else {
            tot += x.toLong()
        }
    }

    println(max)
    println(max.sum())
}

