package d3

import utils.*

fun main() {
    val g = readLongGrid("/d3/d3p1")

    val l = mutableListOf<Long>()
    for (i in 0..g[0].size-1) {
        var ones = 0
        for (line in g) {
            if(line[i] == 1L) {
                ones++
            }
        }
        if (ones > g.size/2) {
            l.add(1)
        } else {
            l.add(0)
        }
    }

    var gamma = 0L
    var ep = 0L
    for (i in 0..l.size-1) {
        val exp = l.size - 1 - i
        val amt = 1.shl(exp)

        if (l[i] == 1L)
            gamma += amt
        else
            ep += amt
    }
    println(ep * gamma)
}
