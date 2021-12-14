package d10

import utils.*
fun main() {
    val list = readList("/d10/d10p1")

    val autoCompleteScores = list.filter{ !corrupt(it) }.map{ autoCompleteScore(it)}
    println(autoCompleteScores.sorted().get(autoCompleteScores.size/2))
}

fun corrupt(line: String): Boolean {
    val openChars = setOf('(', '[', '{', '<')
    val openToClose = mapOf(
        Pair('(', ')'),
        Pair('[', ']'),
        Pair('<', '>'),
        Pair('{', '}')
    )

    val openStack = mutableListOf<Char>()

    for(idx in 0..line.length-1) {
        if (line[idx] in openChars) {
            openStack.add(line[idx])
        } else {
            if (line[idx] != openToClose.getOrDefault(openStack.last(), ' ')) {
                return true
            } else {
                openStack.removeLast()
            }
        }
    }

    return false
}

fun autoCompleteScore(line: String): Long {
    val openChars = setOf('(', '[', '{', '<')
    val openToClose = mapOf(
        Pair('(', ')'),
        Pair('[', ']'),
        Pair('<', '>'),
        Pair('{', '}')
    )
    val closeToScore = mapOf(
        Pair('(', 1),
        Pair('[', 2),
        Pair('<', 4),
        Pair('{', 3)
    )

    val openStack = mutableListOf<Char>()

    for(idx in 0..line.length-1) {
        if (line[idx] in openChars) {
            openStack.add(line[idx])
        } else {
            openStack.removeLast()
        }
    }

    return openStack.reversed().fold(0L, { acc, c ->
        acc*5 + closeToScore.getOrDefault(c, 0)
    })
}

