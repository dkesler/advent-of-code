import intcode.Computer
import intcode.loadProgramFromFile
import intcode.runProgramFromFile

fun main() {

    var target: Pair<Int, Int>? = null
    var dist = 1

    while( target == null) {
        val candidates = genCandidiates(dist)
        val fits = candidates.filter { fits(it) }
        if (fits.isNotEmpty()) {
            target = fits[0]
        } else {
            dist++
        }
    }

    println(target)
}

fun fits(topleft: Pair<Int, Int>): Boolean {
    val x = topleft.first.toLong()
    val y = topleft.second.toLong()
    return runProgramFromFile("src/main/resources/d19p1", listOf(x, y))[0] == 1L
            && runProgramFromFile("src/main/resources/d19p1", listOf(x, y + 100))[0] == 1L
            && runProgramFromFile("src/main/resources/d19p1", listOf(x + 100, y))[0] == 1L
}

fun genCandidiates(dist: Int): List<Pair<Int, Int>> {
    return 0.rangeTo(dist).map { x -> Pair(x, dist-x)  }
}
