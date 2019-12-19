import java.io.File

fun main() {
    val reactions = File("src/main/resources/d14p1").readLines()
        .map(::toReaction)
        .plus(Reaction(listOf(), Reagent("ORE", 1), 1000000000000L, 0, false))
        .map{Pair(it.output.type, it) }.toMap()

    while(reactions.getValue("FUEL").generate(1, reactions)) {}

    println(reactions.getValue("FUEL").generated)
}
