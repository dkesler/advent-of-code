package d7

import intcode.runProgramFromFile

fun main(){
    val permutations: List<List<Int>> = generatePermutations(setOf(0,1,2,3,4))
    println(permutations)

    println(permutations.map{ Pair(it, maxThrust(it)) }.sortedBy { it.second }.reversed()[0])


}

fun maxThrust(phases: List<Int>): Long {
    return phases.fold(0L, { acc, phase ->
        runProgramFromFile("src/main/resources/d7/d7p1", listOf(phase.toLong(), acc))[0]
    })
}

fun generatePermutations(ints: Set<Int>): List<List<Int>> {
    if (ints.isEmpty()) {
        return listOf(listOf())
    }

    return ints.map { i ->generatePermutations(ints - i).map { it + i } }.flatMap { it }
}
