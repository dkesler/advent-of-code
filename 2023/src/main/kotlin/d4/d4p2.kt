package d4

import utils.*

fun main() {
    val lines = readList("/d4.txt")

    val cards = lines.map{ it.split(":")[1] }
            .map {
                val s = it.split(" | ")
                val winning = s[0].split(" ").filter { it != "" }.map { it.toInt() }
                val mine = s[1].split(" ").filter { it != "" }.map { it.toInt() }
                Pair(winning.toSet(), mine.toSet())
            }
    var totalCards = 0
    val toScratch = cards.indices.associateWith { 1 }.toMutableMap()

    while(toScratch.isNotEmpty()) {
        val card = toScratch.keys.first()
        val numCards = toScratch[card]!!
        totalCards += numCards
        toScratch.remove(card)
        val matches = cards[card].first.intersect(cards[card].second).size
        if (matches > 0) {
            ((card+1) until (card+1+matches)).forEach { m ->
                toScratch[m] = toScratch.getOrDefault(m, 0) + numCards
            }
        }
    }

    println(totalCards)
}
