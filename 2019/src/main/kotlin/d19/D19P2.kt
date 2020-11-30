package d19

import intcode.runProgramFromFile

fun main() {

    var target: Pair<Int, Int>? = null
    var candidates = genCandidiates(30)

    while( target == null) {
        candidates = candidates.filter{ inBeam(it) }
        val fits = candidates.filter { fits(it) }
        if (fits.isNotEmpty()) {
            target = fits[0]
        } else {
            candidates = candidates.flatMap{ listOf(Pair(it.first, it.second+1), Pair(it.first+1, it.second)) }.distinct()
        }
    }

    println(target)
}

fun inBeam(it: Pair<Int, Int>): Boolean {
    return runProgramFromFile("2019/src/main/resources/d19/d19p1", listOf(it.first.toLong(), it.second.toLong()))[0] == 1L
}

fun fits(topleft: Pair<Int, Int>): Boolean {
    val x = topleft.first.toLong()
    val y = topleft.second.toLong()

    return runProgramFromFile("2019/src/main/resources/d19/d19p1", listOf(x, y + 99))[0] == 1L
            && runProgramFromFile("2019/src/main/resources/d19/d19p1", listOf(x + 99, y))[0] == 1L
}

fun genCandidiates(dist: Int): List<Pair<Int, Int>> {
    return 0.rangeTo(dist).map { x -> Pair(x, dist-x)  }
}
