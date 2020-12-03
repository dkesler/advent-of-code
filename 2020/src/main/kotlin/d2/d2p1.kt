package d2

data class Policy(val min: Int, val max: Int, val letter: Char) {
    fun matches(candidate: String) : Boolean {
        val count = candidate.filter { it == letter }.count()
        return count in min..max
    }
}

fun toPolicy(line: String): Policy {
    val (range, char) = line.split(' ')
    val (min, max) = range.split('-')
    return Policy(
        Integer.parseInt(min),
        Integer.parseInt(max),
        char[0]
    )
}

fun main() {
    val lines = Class::class.java.getResource("/d2/d2p1").readText()
        .split("\n")
        .filter{it != ""}
    val numValid = lines.filter {
        val (policyStr, password) = it.split(": ")
        val policy = toPolicy(policyStr)
        policy.matches(password)
    }.count()

    println(numValid)
}
