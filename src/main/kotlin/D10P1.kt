import java.io.File

fun main() {
    val astroids = File("src/main/resources/d10p1").readLines()
        .mapIndexed { y, line -> line.split("").mapIndexed { x, char -> Space(x-1, y, char == "#") } }
        .flatMap { it }
        .filter(Space::present)

    println(astroids)

    println(astroids.map{ Pair(it, maxVisible(it, astroids))}.sortedByDescending{ it.second }[0])


}

fun maxVisible(space: Space, astroids: List<Space>): Int {
    fun gt(i1: Int, i2: Int): Boolean {
        return i1 > i2
    }
    fun gte(i1: Int, i2: Int): Boolean {
        return i1 >= i2
    }
    fun lt(i1: Int, i2: Int): Boolean {
        return i1 < i2
    }
    fun lte(i1: Int, i2: Int): Boolean {
        return i1 <= i2
    }

    //q1 | q2
    //   .
    //q4 | q3

    val q1 = getQuadrant(space, astroids, ::lt, ::lte)
    val q2 = getQuadrant(space, astroids, ::gte, ::lt)
    val q3 = getQuadrant(space, astroids, ::gt, ::gte)
    val q4 = getQuadrant(space, astroids, ::lte, ::gt)

    println(space)
    println(q1)
    println(q2)
    println(q3)
    println(q4)

    return visibleFrom(space, q1) + visibleFrom(space, q2) + visibleFrom(space, q3) + visibleFrom(space, q4)

}

fun visibleFrom(space: Space, quadrant: List<Space>): Int {
    return quadrant.map{ (it.y - space.y).toDouble() / (it.x - space.x).toDouble() }.distinct().count()
}

fun getQuadrant(space: Space, astroids: List<Space>, xFun: (Int, Int) -> Boolean, yFun: (Int, Int) -> Boolean): List<Space> {
    return astroids.filter{ xFun(it.x, space.x) && yFun(it.y, space.y) }
}

data class Space(val x: Int, val y: Int, val present: Boolean)
