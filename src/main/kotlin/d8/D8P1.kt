package d8

import java.io.File

fun main() {
    val layerWithFewestZeros = File("src/main/resources/d8/d8p1").readText().trim()
        .split("")
        .filter{ it != "" }
        .map { Integer.parseInt(it) }
        .chunked(25 * 6)
        .map(::Layer)
        .sortedBy { it.digitCount[0] }
        .first()

    println(layerWithFewestZeros.digitCount.getValue(1) * layerWithFewestZeros.digitCount.getValue(2))


}

data class Layer(val layer: List<Int>) {
    val digitCount: Map<Int, Int>
    init {
        digitCount = layer.groupBy { it }.mapValues { it.value.size }.withDefault { 0 }

    }

    fun mergeLayer(other: Layer): Layer {
        fun mergePair(pair: Pair<Int, Int>): Int {
            if (pair.first == 2) {
                return pair.second
            } else {
                return pair.first
            }
        }
        return Layer(
            layer.zip(other.layer).map(::mergePair)
        )
    }

    fun prettyPrint() {
        fun printPixel(i: Int) {
            if (i == 0) {
                print(" ")
            } else {
                print("X")
            }
        }
        layer.chunked(25).forEach{
            it.forEach(::printPixel)
            println("")
        }
    }

}

