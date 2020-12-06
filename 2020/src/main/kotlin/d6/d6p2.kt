package d6

fun main() {
    val content = Class::class.java.getResource("/d6/d6p1").readText()
    val list = content.split("\n")

    val groups: MutableList<MutableSet<Char>> = mutableListOf<MutableSet<Char>>()
    var answers: MutableSet<Char> = mutableSetOf<Char>()
    var newGroup = true
    list.forEach{ line ->
        if (line.isEmpty()) {
            groups.add(answers)
            newGroup = true
        } else {
            if (newGroup) {
                answers = line.toList().toMutableSet()
                newGroup = false
            } else {
                answers = answers.intersect(line.toSet()).toMutableSet()
            }
        }
    }

    println(groups.map{it.size}.sum())



}
