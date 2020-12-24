package d22

fun main() {
    val content = Class::class.java.getResource("/d22/d22p1").readText()
    val list = content.split("\n").filter{it != ""}

    val player2 = list.indexOfFirst { it.equals("Player 2:") }

    val deck1 = list.subList(1, player2).map{ it.toInt() }.toMutableList()
    val deck2 = list.subList(player2+1, list.size).map{ it.toInt() }.toMutableList()

    while (deck1.isNotEmpty() && deck2.isNotEmpty()) {
        val c1 = deck1[0]
        val c2 = deck2[0]
        if (c1 > c2) {
            deck1.removeAt(0)
            deck2.removeAt(0)

            deck1.add(c1)
            deck1.add(c2)

        } else {
            deck1.removeAt(0)
            deck2.removeAt(0)
            deck2.add(c2)
            deck2.add(c1)
        }
    }

    val winningDeck = if (deck1.isNotEmpty()) deck1 else deck2

    val score = winningDeck.mapIndexed{
        i, c ->
        (winningDeck.size - i) * c
    }.sum()

    println(score)



}
