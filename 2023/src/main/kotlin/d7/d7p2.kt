package d7

import utils.*

data class Hand2(val cards: List<Int>, val bid: Int) {
    fun compareTo(b: Hand2): Int {
        if (handValue() > b.handValue()) {
            return 1
        } else if (handValue() < b.handValue()) {
            return -1
        } else {
            if (cards[0] > b.cards[0]) {
                return 1
            } else if (cards[0] < b.cards[0]) {
                return -1
            } else  if (cards[1] > b.cards[1]) {
                return 1
            } else if (cards[1] < b.cards[1]) {
                return -1
            } else if (cards[2] > b.cards[2]) {
                return 1
            } else if (cards[2] < b.cards[2]) {
                return -1
            } else if (cards[3] > b.cards[3]) {
                return 1
            } else if (cards[3] < b.cards[3]) {
                return -1
            } else if (cards[4] > b.cards[4]) {
                return 1
            } else {
                return -1
            }
        }
    }

    fun handValue() : Int {
        val m = cards.groupBy{it}.mapValues{it.value.size}
        val nonJokers = m.filter{ it.key != 1 }
        val numJokers = m.getOrDefault(1, 0)

        //This is way too hacky and copy&paste.  When writing this, I didn't put together the fact that
        //you can always just add numJokers to the most common card value to get the best hand, which
        //would have allowed for not needing different cases for each numJokers
        if (numJokers >= 4) return 10

        if (numJokers == 3) {
            if (nonJokers.values.any{it  == 2}) return 10
            return 9
        }

        if (numJokers == 2) {
            if (nonJokers.values.any{ it == 3}) return 10
            if (nonJokers.values.any{it == 2}) return 9
            return 7
        }

        if (numJokers == 1) {
            if (nonJokers.values.any{ it == 4}) return 10
            if (nonJokers.values.any{it == 3 }) return 9
            if (nonJokers.values.count{it == 2} == 2) return 8
            if (nonJokers.values.any{it == 2}) return 7
            return 5
        }

        if (nonJokers.values.any{it == 5}) return 10
        if (nonJokers.values.any{ it == 4}) return 9
        if (nonJokers.values.any{ it  == 3} && m.values.any{it == 2}) return 8
        if (nonJokers.values.any{it  == 3 }) return 7
        if (nonJokers.values.count{it  == 2} == 2) return 6
        if (nonJokers.values.any{it  == 2}) return 5
        return 4
    }
}

fun main() {
    val lines = readList("/d7.txt")

    val hands = lines.map {
        val s = it.split(" ").filter { it != "" }
        Hand2(s[0].map { c -> toInt2(c) }, s[1].toInt())
    }

    val sorted = hands.sortedWith{ a: Hand2, b: Hand2 -> a.compareTo(b) }

    println(
            sorted.mapIndexed{ idx, hand -> (idx+1) * hand.bid.toLong()}.sum()
    )
}

fun toInt2(c: Char): Int {
    return when (c) {
        '2' -> 2
        '3' -> 3
        '4' -> 4
        '5' -> 5
        '6' -> 6
        '7' -> 7
        '8' -> 8
        '9' -> 9
        'T' -> 10
        'J' -> 1
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> {throw Error()}
    }
}
