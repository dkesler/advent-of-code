package d3

import utils.*

fun main() {
    val lines = readList("/d3.txt")
    println(lines.map{ doMults(it)}.sum())
}

fun doMults(line: String):Long {
    var sum = 0L
    val r = Regex("mul\\(([0-9]+),([0-9]+)\\)")
    val matches = r.findAll(line)
    for (match in matches) {
        sum += match.groupValues[1].toLong() * match.groupValues[2].toLong()
    }
    return sum

}
