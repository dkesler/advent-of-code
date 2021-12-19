package d17

import utils.*

//This approach was terrible and obviated by calculating all possible velocity pairs in part 2
//I leave this here as a testament to my shame
fun main() {
    val xMin = 248
    val xMax = 285
    val yMin = -85
    val yMax = -56

    val highestY = findHighestVY2(xMin, xMax, yMin, yMax)
    //val highestY = findHighestVY2(20, 30, -10, -5)
    println(highestY)
    println((1..xMax).map{ highestY.second * it - tri(it-1) }.maxOrNull()!!)
}
fun findHighestVY2(xMin: Int, xMax: Int, yMin: Int, yMax: Int): Pair<Int, Int> {
    val optimalVxs = findOptimalXs(xMin, xMax)

    val workingVs = optimalVxs.map { vx ->
        findWorkingVys(vx, yMin, yMax).map{Pair(vx, it)}
    }.flatten()

    return workingVs.sortedByDescending { it.second }.first()
}

//this is technically finding some non-working vys because the probe would not be
//in position along the x axis, but it always does so in a way that the non-working vys
//are too small and so will be filtered out
fun findWorkingVys(vx: Int, yMin: Int, yMax: Int): List<Int> {
    val possibleVs = (yMin..yMax).map { y ->
        (2*y..vx).map { t ->
            y.toDouble() / t + (t - 1) / 2.0
        }
    }.flatten()
    return possibleVs.filter{it.toInt().toDouble() == it}
        .map{it.toInt()}
}

fun findOptimalXs(xMin: Int, xMax: Int): List<Int> {
    return (1..xMax).filter{
        val xEnd = tri(it)
        xEnd >= xMin && xEnd <= xMax
    }
}

fun tri(i: Int): Int {
    return i * (i+1) / 2
}
