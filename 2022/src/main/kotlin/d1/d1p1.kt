package d1

import utils.*

fun main() {
    val content = {}.javaClass.getResource("/d1.txt")!!.readText()
    val list = content.split("\n")

    var max = 0L
    var tot = 0L
    for (x in list) {
        if (x == "") {
            if (tot > max) {
                max = tot
            }
            tot = 0
        } else {
            tot += x.toLong()
        }
    }

    println(max)
}

