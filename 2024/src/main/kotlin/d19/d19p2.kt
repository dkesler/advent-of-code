package d19

import utils.*

fun main() {
    val lines = readList("/d19.txt")
    val towels = lines[0].split(",").filter{ it.isNotBlank() }.map{ it.trim() }
    val patterns = lines.subList(1, lines.size)

    val counts = patterns.map { legalPatterns(it, towels) }
    println(counts)
    println(counts.sum())
}

val patternCounts = mutableMapOf<String, Long>()
fun legalPatterns(pattern: String, towels: List<String>): Long {
    if (pattern in patternCounts.keys) return patternCounts[pattern]!!
    if (pattern.isBlank()) return 1

    var counts = 0L
    for (towel in towels) {
        if (pattern.startsWith(towel)) {
            val subCounts = legalPatterns(pattern.substring(towel.length), towels)
            counts += subCounts
        }
    }
    patternCounts[pattern] = counts
    return counts

}
