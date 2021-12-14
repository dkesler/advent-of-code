package d14

import utils.*

fun main() {
    val list =  readList("/d14/d14p1")
    val template = list[0]
    val instr = list.subList(1, list.size).map{it.split(" -> ")}.map{Pair(it[0], it[1])}.toMap()

    val res = (1..40).fold(template, { acc, i ->
        val x = substitute(acc, instr)
        x
    })
    val m = res.groupBy { it }.mapValues { it.value.size }.toList().sortedBy { it.second }
    println(m.last().second - m.first().second)
}

fun substitute(template: String, instr: Map<String, String>): String {
    var result = ""
    for (i in 0..template.length-2) {
        val x = String(listOf(template[i], template[i+1]).toCharArray())
        if (x in instr.keys) {
            result += template[i] + instr.getOrDefault(x, "")
        } else {
            result += template[i]
        }
    }
    result += template[template.length-1]
    return result
}
