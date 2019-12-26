package d3

import java.io.File

fun main() {
    val wires = File("src/main/resources/d3/d3p1").readLines()
        .map(::toWire)

    println(findMinCrossingTiming(wires[0], wires[1]))
}

fun findMinCrossingTiming(w1: List<Move>, w2: List<Move>): Int? {
    val points: List<Crossing> = findCrossings(w1, w2)
    return points.map(Crossing::timing).min()
}


