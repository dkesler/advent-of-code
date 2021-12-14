package d14

import utils.*

fun main() {
    val list =  readList("/d14/d14p1")
    val template = list[0].toList()
    val instr = list.subList(1, list.size).map{it.split(" -> ")}.map{Pair(it[0].toList(), it[1][0])}.toMap()

    val res = charCount(template, instr, 40)

    val m = res.toList().sortedBy { it.second }
    println(m.last().second - m.first().second)
}

fun charCount(template: List<Char>, instr: Map<List<Char>, Char>, totalIters: Int): Map<Char, Long> {
    val memoTable = mutableMapOf<Pair<List<Char>, Int>, Map<Char, Long>>()

    fun expand(token: List<Char>, iters: Int): Map<Char, Long> {
        if (iters == 0) {
            return mapOf(Pair(token[0], 1))
        }

        val memoKey = Pair(token, iters)
        if (memoTable.containsKey(memoKey)) {
            return memoTable.getOrDefault(memoKey, mapOf())
        }

        if (!instr.containsKey(token))  {
            return token.groupBy { it }.mapValues { it.value.size.toLong() }
        }

        val insertChar = instr.getOrDefault(token, ' ')

        val left = expand(listOf(token[0], insertChar), iters-1)
        val right = expand(listOf(insertChar, token[1]), iters-1)

        val res = mergeMaps(left, right)

        memoTable[memoKey] = res
        return res
    }
    val res = (template.indices).fold(mapOf<Char, Long>(), { m, i ->
        val expanded = expand(template.subList(i, Math.min(i+2, template.size)), totalIters)
        mergeMaps(m, expanded)
    })
    return res
}

fun mergeMaps(left: Map<Char, Long>, right: Map<Char, Long>) =
    (left.keys + right.keys).map {
        Pair(it, left.getOrDefault(it, 0L) + right.getOrDefault(it, 0L))
    }.toMap()


