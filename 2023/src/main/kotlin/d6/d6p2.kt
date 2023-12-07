package d6

import utils.*

fun main() {
    val races = listOf<Pair<Long, Long>>(
            Pair(47707566, 282107911471062),
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