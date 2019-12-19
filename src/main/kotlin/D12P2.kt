import java.io.File

fun main() {
    var planets =
        File("src/main/resources/d12p1")
            .readLines()
            .map(::parsePlanet)
            .map{ Planet1D(it.z) }
    //lcm the following
    //x: 186028
    //y: 135024
    //z: 193052

    val next = copy(planets)

    var ticks = 0
    val allPrevious = mutableSetOf<List<Planet1D>>()

    while (!allPrevious.contains(planets) || !allPrevious.contains(next)) {
        planets = copy(next)
        allPrevious.add(planets)

        for (p1 in next) {
            for (p2 in next) {
                p1.adjustV(p2)
            }
        }

        for (p1 in next) {
            p1.adjustP()
        }

        ticks++

        if (ticks % 100000 == 0) {
            println(ticks)
        }
    }

    println(allPrevious.indexOf(next))

    println(ticks)
}

private fun copy(planets: List<Planet1D>) = planets.map(Planet1D::cp)

//x: 2028
//y: 5898
//z: 4702

data class Planet1D(var x: Int, var vx: Int = 0) {
    fun adjustV(other: Planet1D) {
        vx +=  signum(other.x - x)
    }

    fun adjustP() {
        x += vx
    }

    fun cp(): Planet1D {
        return Planet1D(x, vx)
    }
}
