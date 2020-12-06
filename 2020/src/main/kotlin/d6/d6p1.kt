package d6

fun main() {
    val content = Class::class.java.getResource("/d6/d6p1").readText()
    val list = content.split("\n")

    val groups: MutableList<MutableSet<Char>> = mutableListOf<MutableSet<Char>>()
    var answers = mutableSetOf<Char>()
    list.forEach{ line ->
        if (line.isEmpty()) {
            groups.add(answers)
            answers = mutableSetOf<Char>()
        } else {
            line.forEach{ c -> answers.add(c) }
        }
    }

    println(groups.map{it.size}.sum())



}
