package d5

import utils.*

fun main() {
    val rules = readList("/d5.txt").map{ it.split("|").map{it.toLong()}.toList()}
    val reports = readList("/d5-2.txt").map{ it.split(",").map{it.toLong()}.toList() }
    val rulesMap = rules.groupBy({it[0]}){it[1]}.mapValues { it.value.toSet() }

    println(reports.filter{ !inOrder(it, rulesMap) }
        .map{ reorder(it, rulesMap) }
        .map{ it[ (it.size-1)/2 ]}.sum())
}

fun reorder(report: List<Long>, rulesMap: Map<Long, Set<Long>>): List<Long> {
    return report.sortedWith{
        x, y ->
        if (rulesMap[x]!!.contains(y)) -1
        else 1
    }
}
