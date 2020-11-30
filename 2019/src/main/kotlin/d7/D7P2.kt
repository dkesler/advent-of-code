package d7

import intcode.Computer
import intcode.loadProgramFromFile

fun main(){
    val permutations: List<List<Int>> = generatePermutations(setOf(5, 6, 7, 8, 9))

    println(permutations.map{ Pair(it, maxThrustFeedback(it)) }.sortedBy { it.second }.reversed()[0])


}

fun maxThrustFeedback(phases: List<Int>): Long {
    val amps: List<Computer> = (0..4).map { Computer(loadProgramFromFile("2019/src/main/resources/d7/d7p1")) }

    val output = (0..4).fold(
        0L,
        { acc, i -> amps[i].runBlockingProgram(listOf(phases[i].toLong(), acc)).second.get(0) }
    )

    return doLoop(amps, output)
}

fun doLoop(amps: List<Computer>, input: Long): Long {
    val a = amps[0].runBlockingProgram(listOf(input)).second.get(0)
    val b = amps[1].runBlockingProgram(listOf(a)).second.get(0)
    val c = amps[2].runBlockingProgram(listOf(b)).second.get(0)
    val d = amps[3].runBlockingProgram(listOf(c)).second.get(0)
    val e = amps[4].runBlockingProgram(listOf(d))

    if(e.first) {
        return doLoop(amps, e.second.get(0))
    } else {
        return e.second.get(0)
    }
}

