package d10
import utils.*

fun main() {
    val list = readList("/d10/d10p1")

    println(list.map{ corruptionScore(it)}.sum())
}

fun corruptionScore(line: String): Int {
    val openChars = setOf('(', '[', '{', '<')
    val openToClose = mapOf(
        Pair('(', ')'),
        Pair('[', ']'),
        Pair('<', '>'),
        Pair('{', '}')
    )
    val closeToScore = mapOf(
        Pair(')', 3),
        Pair(']', 57),
        Pair('>', 25137),
        Pair('}', 1197)
    )

    val openStack = mutableListOf<Char>()

    for(idx in 0..line.length-1) {
        if (line[idx] in openChars) {
            openStack.add(line[idx])
        } else {
            if (line[idx] != openToClose.getOrDefault(openStack.last(), ' ')) {
                return closeToScore.getOrDefault(line[idx], 0)
            } else {
                openStack.removeLast()
            }
        }
    }

    return 0

}
