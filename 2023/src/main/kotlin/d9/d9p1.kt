package d9

import utils.*

fun main() {
    val lines = readList("/d9.txt")

    val l = lines.map{ it.split(" ").filter{it != "" }.map{it.toLong()} }

    println(l.map{ getNextPrediction(it)}.sum())
}

fun getNextPrediction(it: List<Long>): Long {
    val seqs = mutableListOf(it)
    while(seqs.last().any{ it != 0L }) {
        val last = seqs.last()
        seqs.add(last.indices.toList().dropLast(1).map{ last[it+1] - last[it] })
    }

    return seqs.reversed().drop(1).fold(0L) { acc, seq ->
        seq.last() + acc
    }
}
