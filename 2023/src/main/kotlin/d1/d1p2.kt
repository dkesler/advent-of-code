package d1

import utils.*

fun main() {
    val lines = readList("/d1.txt")
    println(
    lines.sumBy{
        val r = "0|1|2|3|4|5|6|7|8|9|zero|one|two|three|four|five|six|seven|eight|nine|ten".toRegex()
        val rBack = "0|1|2|3|4|5|6|7|8|9|zero|one|two|three|four|five|six|seven|eight|nine|ten".reversed().toRegex()

        val first = r.find(it, 0)
        val last = rBack.find(it.reversed(), 0)
            toI(first!!.value) * 10 + toI(last!!.value.reversed())
    }
    )
}

fun toI(first: String): Int {
    return when(first) {
        "zero" -> 0
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        "ten" -> 10
        "1" -> 1
        "2" -> 2
        "3" -> 3
        "4" -> 4
        "5" -> 5
        "6" -> 6
        "7" -> 7
        "8" -> 8
        "9" -> 9
        "0" -> 0
        else -> {0}
    }
}
