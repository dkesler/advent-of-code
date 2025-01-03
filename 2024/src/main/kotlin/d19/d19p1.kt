package d19

import utils.*

fun main() {
    val lines = readList("/d19.txt")
    val towels = lines[0].split(",").filter{ it.isNotBlank() }.map{ it.trim() }
    val patterns = lines.subList(1, lines.size)

    println(patterns.filter{ canBeMade(it, towels)}.count())
}

val makable = mutableMapOf<String, Boolean>()

fun canBeMade(pattern: String, towels: List<String>): Boolean {
    if (pattern in makable.keys) return makable[pattern]!!
    if (pattern.isBlank()) return true

    for (towel in towels) {
        if (pattern.startsWith(towel)) {
            if (canBeMade(pattern.substring(towel.length), towels)) {
                makable[pattern] = true
                return true
            }
        }
    }
    makable[pattern] = false
    return false
}
