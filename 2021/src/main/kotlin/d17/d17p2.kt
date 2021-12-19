package d17

fun main() {
    val xMin = 248
    val xMax = 285
    val yMin = -85
    val yMax = -56

    val allVs = findAllVs(xMin, xMax, yMin, yMax)
    //val allVs = findAllVs(20, 30, -10, -5)
    val maxVY = allVs.maxOf { it.second }
    println(maxVY)
    println((1..xMax).map{ maxVY * it - tri(it-1) }.maxOrNull()!!)
    println(allVs.size)
}

fun findAllVs(xMin: Int, xMax: Int, yMin: Int, yMax: Int): Set<Pair<Int, Int>> {
    val validVXs = findValidVXs(xMin, xMax)
    return validVXs.map{vx -> findValidVys(vx, xMin, xMax, yMin, yMax).map{Pair(vx, it)}}.flatten().toSet()
}

fun findValidVXs(xMin: Int, xMax: Int): List<Int> {
    return (1..xMax).filter { vx ->
        (1..xMax).map{ t-> calcX(vx, t)
        }.any { xMin <= it && it <= xMax  }
    }
}

fun findValidVys(vx: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int): List<Int> {
    val possibleVYs = (yMin..yMax).map { y ->
        //for a given target y, vy = y/t +(t-1)/2
        //in order for this to have an integer solution, t can be at most |2y| since the (t-1)/2 solution
        //will either be an integer or an integer + 0.5.
        //we must additionally ensure that the value for t will also work for x
        (1..Math.abs(2*y)).filter { t->
            val x = calcX(vx, t)
            x >= xMin && x <= xMax
        }.map { t ->
            //this is vy for a given t and target y position.  vy may not be integer though
            //in which case we will filter it out later
            y.toDouble() / t + (t - 1) / 2.0
        }
    }.flatten()
    return possibleVYs.filter{it.toInt().toDouble() == it}
        .map{it.toInt()}
}

private fun calcX(vx: Int, t: Int): Int {
    if (t <= vx) {
        return vx*t - tri(t-1)
    } else {
        return tri(vx)
    }
}

