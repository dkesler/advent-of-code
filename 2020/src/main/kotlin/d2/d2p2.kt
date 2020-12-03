package d2

data class Policy2(val pos1: Int, val pos2: Int, val letter: Char) {
    fun matches(candidate: String) : Boolean {
        return (candidate[pos1-1] == letter) xor (candidate[pos2-1] == letter)
    }
}

fun toPolicy2(line: String): Policy2 {
    val (range, char) = line.split(' ')
    val (min, max) = range.split('-')
    return Policy2(
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
        val policy = toPolicy2(policyStr)
        policy.matches(password)
    }.count()

    println(numValid)
}
