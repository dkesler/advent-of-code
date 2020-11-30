package d22

import java.io.File
import java.lang.RuntimeException
import java.util.regex.Pattern

fun main() {
    val deck = (0..10006L).toList()

    val moves = File("2019/src/main/resources/d22/d22p1").readLines()
        .map(::toShuffle)

    val shuffled = moves.fold(deck, { acc, op -> op(acc) })

    println(shuffled.size)
    println(shuffled.indexOf(2019))
}

fun toShuffle(line: String): (List<Long>) -> List<Long> {
    val incMatcher = Pattern.compile("deal with increment ([0-9]+)").matcher(line)
    if (incMatcher.matches()) {
        return { incrementN(it, Integer.parseInt(incMatcher.group(1))) }
    }

    val cutMatcher = Pattern.compile("cut (-?[0-9]+)").matcher(line)
    if (cutMatcher.matches()) {
        return { cutN(it, Integer.parseInt(cutMatcher.group(1))) }
    }

    val newMatcher = Pattern.compile("deal into new stack").matcher(line)
    if (newMatcher.matches()) {
        return ::newStack
    }

    throw RuntimeException("Unparseable line $line")
}

fun newStack(deck: List<Long>): List<Long> {
    return deck.reversed()
}

fun cutN(deck: List<Long>, cut: Int): List<Long> {
    if (cut < 0) {
        return cutN(deck, deck.size + cut)
    }

    return deck.subList(cut, deck.size) + deck.subList(0, cut)
}

fun incrementN(deck: List<Long>, inc: Int): List<Long> {
    var target = 0
    val newDeck = deck.toMutableList()
    for (card in deck) {
        newDeck.set(target, card)
        target = (target + inc) % deck.size
    }

    return newDeck.toList()
}
