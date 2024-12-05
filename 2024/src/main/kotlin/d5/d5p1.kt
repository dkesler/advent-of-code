package d5

import utils.*

fun inOrder(list: List<Long>, rulesMap: Map<Long, Set<Long>>): Boolean {
    val pagesSeen = mutableSetOf<Long>()
    for (page in list) {
        for (seen in pagesSeen) {
            if (!rulesMap[seen]!!.contains(page)) return false
        }
        pagesSeen.add(page)
    }
    return true
}

fun main() {
    val rules = readList("/d5.txt").map{ it.split("|").map{it.toLong()}.toList()}
    val reports = readList("/d5-2.txt").map{ it.split(",").map{it.toLong()}.toList() }
    val rulesMap = rules.groupBy({it[0]}){it[1]}.mapValues { it.value.toSet() }

    println(reports.filter{ inOrder(it, rulesMap) }.map{ it[ (it.size-1)/2 ]}.sum())
}