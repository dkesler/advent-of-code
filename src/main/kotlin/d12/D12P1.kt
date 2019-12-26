package d12

import java.io.File
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val planets = File("src/main/resources/d12/d12p1").readLines()
        .map(::parsePlanet)




    for (tick in 1..1000) {
        for (p1 in planets) {
            for (p2 in planets) {
                p1.adjustV(p2)
            }
        }

        for (p1 in planets) {
            p1.adjustP()
        }
    }

    println(planets.map(Planet::totalEnergy).sum())
}

fun signum(i: Int): Int {
    if (i > 0) {
        return 1
    } else if (i < 0) {
        return -1
    } else {
        return 0
    }
}

data class Planet(var x: Int, var y: Int, var z: Int, var vx: Int = 0, var vy: Int = 0, var vz: Int = 0) {
    fun adjustV(other: Planet) {
        vx +=  signum(other.x - x)
        vy +=  signum(other.y - y)
        vz +=  signum(other.z - z)
    }

    fun adjustP() {
        x += vx
        y += vy
        z += vz
    }

    fun totalEnergy(): Int {
        return (abs(x) + abs(y) + abs(z)) * (abs(vx) + abs(vy) + abs(vz))
    }

    fun cp(): Planet {
        return Planet(x, y, z, vx, vy, vz)
    }
}

fun parsePlanet(str: String): Planet {
    val matcher = Pattern.compile("^<x=(.*), y=(.*), z=(.*)>$").matcher(str)
    matcher.matches()
    val planet = Planet(
        Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2)),
        Integer.parseInt(matcher.group(3))
    )
    return planet
}
