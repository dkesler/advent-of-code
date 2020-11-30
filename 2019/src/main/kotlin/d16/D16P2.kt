package d16

import java.io.File
import kotlin.math.abs

fun main() {
    val rawInput = File("2019/src/main/resources/d16/d16p1").readLines()
        .get(0)
        .trim()

    val input = rawInput
        .split("")
        .filter{ it.isNotBlank() }
        .map{ Integer.parseInt(it) }

    val repeatedInput = (1..10000).flatMap{input}

    val output = (1..100).fold(repeatedInput, {acc, i -> println("iteration $i"); fft2(acc) })

    val offset = Integer.parseInt(rawInput.substring(0, 7))

    println(output.drop(offset).take(8))
}

