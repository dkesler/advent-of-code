package d10

import java.io.File

fun main() {
    val astroids = File("src/main/resources/d10/d10p1").readLines()
        .mapIndexed { y, line -> line.split("").mapIndexed { x, char -> Space(x-1, y, char == "#") } }
        .flatMap { it }
        .filter(Space::present)

    println(astroids)
    //real data
    val home = Space(26, 36, true)
    //test home
    //val home = Space(11, 13, true)

    var astroidsByPolar: List<SpaceDirection> = toPolar(home, astroids.filter{it != home} )

    val destructionOrder = mutableListOf<Space>()
    while(astroidsByPolar.isNotEmpty()) {
        val destroyedThisCycle = astroidsByPolar.map { it.takeNext() }
        destructionOrder.addAll(destroyedThisCycle)
        astroidsByPolar = astroidsByPolar.filter { it.astroids.isNotEmpty() }
    }

    destructionOrder.forEachIndexed{ i, space -> println("${i+1}: $space") }
}

fun toPolar(home: Space, others: List<Space>): List<SpaceDirection> {
    return others.groupBy { toTheta(home, it) }.map { SpaceDirection(it.key, it.value.sortedBy { dist(home, it) }.toMutableList()) }.sortedBy { it.theta }
}

fun dist(home: Space, target: Space): Double {
    return Math.sqrt(Math.pow(target.x.toDouble() - home.x, 2.0) + Math.pow(target.y.toDouble() - home.y, 2.0))
}

fun toTheta(home: Space, target: Space): Double {
    val theta = Math.atan2(target.x.toDouble() - home.x, target.y.toDouble() - home.y)
    //the laser starts aiming up and goes clockwise, so we adjust so that we start at 0 and go up from there
    val theta2 = 2 * Math.PI - theta - Math.PI
    if (theta2 < 0) {
        return theta2 + 2 * Math.PI
    } else {
        return theta2
    }
}

data class SpaceDirection(val theta: Double, val astroids: MutableList<Space>) {
    fun takeNext(): Space {
        return astroids.removeAt(0)
    }
}
