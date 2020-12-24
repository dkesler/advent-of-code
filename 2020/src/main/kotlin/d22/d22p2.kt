package d22

import java.util.*

fun playGame(deck1: List<Int>, deck2: List<Int>): Pair<Int, List<Int>> {
    val d1 = deck1.toMutableList()
    val d2 = deck2.toMutableList()

    val prevRounds = mutableSetOf<Pair<List<Int>, List<Int>>>()

    while (d1.isNotEmpty() && d2.isNotEmpty()) {
        if (prevRounds.contains(Pair(d1.toList(), d2.toList()))) {
            return Pair(1, d1)
        } else {
            prevRounds.add(Pair(d1.toList(), d2.toList()))
        }

        val c1 = d1[0]
        d1.removeAt(0)
        val c2 = d2[0]
        d2.removeAt(0)

        if (c1 <= d1.size && c2 <= d2.size) {
            val (winner, x) = playGame(
                d1.subList(0, c1),
                d2.subList(0, c2)
            )
            if (winner == 1) {
                d1.add(c1)
                d1.add(c2)
            } else {
                d2.add(c2)
                d2.add(c1)
            }
        } else {
            if (c1 > c2) {
                d1.add(c1)
                d1.add(c2)
            } else {
                d2.add(c2)
                d2.add(c1)
            }
        }
    }

    if (d1.isNotEmpty()) {
        return Pair(1, d1)
    } else {
        return Pair(2, d2)
    }
}

fun main() {


    val content = Class::class.java.getResource("/d22/d22p1").readText()
    val list = content.split("\n").filter{it != ""}

    val player2 = list.indexOfFirst { it.equals("Player 2:") }

    val deck1 = list.subList(1, player2).map{ it.toInt() }.toMutableList()
    val deck2 = list.subList(player2+1, list.size).map{ it.toInt() }.toMutableList()

    val (_, winningDeck) = playGame(deck1, deck2)

    val score = winningDeck.mapIndexed{
        i, c ->
        (winningDeck.size - i) * c
    }.sum()

    println(score)



}
