package d3

import utils.*

fun main() {
    val lines = readList("/d3.txt").flatMap { it.toList() }.joinToString("")
    println(doMults2(lines))


}
//184122457
fun doMults2(line: String):Long {
    var sum = 0L
    var mulOn = true
    val r = Regex("(mul)\\(([0-9]+),([0-9]+)\\)|(do)\\(\\)|(don't)\\(\\)")
    val matches = r.findAll(line)
    for (match in matches) {
        if (match.groupValues[1] == "mul" && mulOn) {
            sum += match.groupValues[2].toLong() * match.groupValues[3].toLong()
        } else if (match.groupValues[4] == "do") {
            mulOn = true
        } else if (match.groupValues[5] == "don't") {
            mulOn = false
        }
    }
    return sum

}
