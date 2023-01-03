package d25

import utils.readList

fun main() {
    val list = readList("/d25.txt")

    println(toSnafu(list.sumOf { fromSnafu(it) }))
}

fun toSnafu(d: Long): String {
    var s = ""
    var remainder = d
    while(remainder > 0) {
        when (remainder % 5) {
            0L -> s = "0$s"
            1L -> s = "1$s"
            2L -> s = "2$s"
            3L -> {
                s = "=$s"
                remainder += 3
            }
            else -> {
                s = "-$s"
                remainder += 2
            }
        }

        remainder /= 5
    }

    return s
}

fun fromSnafu(s: String): Long {
    var total = 0L
    var power = 1L
    for (c in s.reversed()) {
        total += when(c) {
            '2' -> 2 * power
            '1' -> 1 * power
            '0' -> 0
            '-' -> -1 * power
            else -> -2 * power
        }
        power *= 5
    }
    return total
}
