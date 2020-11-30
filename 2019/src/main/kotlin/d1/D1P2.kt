package d1

import java.io.File

fun toFuelRecursive(mass: Int): Int {
    val newFuel = (mass / 3) - 2

    if (newFuel <= 0) {
        return 0;
    } else {
        return newFuel + toFuelRecursive(newFuel)
    }
}

fun main() {
    val fuel = File("2019/src/main/resources/d1/d1p1").readLines()
        .map { Integer.parseInt(it) }
        .map(::toFuelRecursive)
        .sum()

    println(fuel)
}
