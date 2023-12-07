package d6

import utils.*

fun main() {
    val races = listOf<Pair<Int, Int>>(
            Pair(47, 282),
            Pair(70, 1079),
            Pair(75, 1147),
            Pair(66, 1062)
    )

    println(
        races.map { race ->
            val time = race.first
            val record = race.second

            (0..time).count { chargingTime ->
                (time - chargingTime) * chargingTime > record
            }
        }.reduce{ a, b -> a * b }
    )
}