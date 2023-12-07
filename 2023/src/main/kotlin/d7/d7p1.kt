package d7

import utils.*

data class Hand(val cards: List<Int>, val bid: Int) {
    fun compareTo(b: Hand): Int {
        if (handValue() > b.handValue()) {
            return 1
        } else if (handValue() < b.handValue()) {
            return -1
        } else {
            //this is as terrible as it looks.  
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

        if (m.values.any{it == 5}) return 10
        if (m.values.any{ it == 4}) return 9
        if (m.values.any{ it == 3} && m.values.any{it == 2}) return 8
        if (m.values.any{it == 3}) return 7
        if (m.values.count{it == 2} == 2) return 6
        if (m.values.any{it == 2}) return 5
        return 4
    }
}

fun main() {
    val lines = readList("/d7.txt")

    val hands = lines.map {
        val s = it.split(" ").filter { it != "" }
        Hand(s[0].map { c -> toInt(c) }, s[1].toInt())
    }

    val sorted = hands.sortedWith{ a: Hand, b: Hand -> a.compareTo(b) }

    println(
            sorted.mapIndexed{ idx, hand -> (idx+1) * hand.bid.toLong()}.sum()
    )
}

fun toInt(c: Char): Int {
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
        'J' -> 11
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> {throw Error()}
    }
}
