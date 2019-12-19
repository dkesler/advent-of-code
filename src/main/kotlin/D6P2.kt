import java.io.File

fun main() {
    val orbits = File("src/main/resources/d6p1").readLines()
        .map(::toOrbitPair)
        .fold(
            mapOf(),
            ::accMap
        )

    val youAncenstry: List<Pair<String, Int>> = getAncestryOf("YOU", orbits)
    val sanAncestry = getAncestryOf("SAN", orbits)
    val closestCommonAncestor: Pair<String, Int> = closestCommonAncestor(youAncenstry, sanAncestry)
    println(closestCommonAncestor)

}

fun closestCommonAncestor(
    a1: List<Pair<String, Int>>,
    a2: List<Pair<String, Int>>
): Pair<String, Int> {
    val a1Map = mapOf<String, Int>().plus(a1)

    val common = a2.dropWhile { !a1Map.containsKey(it.first) }[0]

    return Pair(common.first, common.second + a1Map.getValue(common.first))
}

fun getAncestryOf(s: String, orbits: Map<String, Tree>): List<Pair<String, Int>> {
    val ancestors = mutableListOf<Pair<String, Int>>()
    var cur = s
    var steps = 0
    while (cur != "COM") {
        cur = orbits.getValue(cur).parent
        ancestors.add(Pair(cur, steps))
        steps += 1
    }

    return ancestors.toList()
}
