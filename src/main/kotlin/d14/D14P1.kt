package d14

import java.io.File

fun main() {
    val reactions = File("src/main/resources/d14/d14p1-test3").readLines()
        .map(::toReaction)
        .plus(Reaction(listOf(), Reagent("ORE", 1)))
        .map{Pair(it.output.type, it) }.toMap()

    reactions.getValue("FUEL").generate(1, reactions)
    println(reactions.getValue("ORE").generated)
    println(reactions)
}

fun toReaction(line: String): Reaction {
    val split = line.split("=>")
    val output = toReagent(split[1])
    val inputs = split[0].split(",").map(::toReagent)
    return Reaction(inputs, output)
}

fun toReagent(str: String): Reagent {
    val split = str.trim().split(" ")
    val amount = Integer.parseInt(split[0])
    val type = split[1]
    return Reagent(type, amount)
}

data class Reagent(val type: String, val amount: Int)
data class Reaction(val inputs: List<Reagent>, val output: Reagent, var spare: Long = 0, var generated: Int = 0, val generateable: Boolean = true) {
    fun generate(requested: Int, reactions: Map<String, Reaction>): Boolean {
        while(spare < requested && generateable) {
            if (inputs.map{reactions.get(it.type)!!.generate(it.amount, reactions)}.any { !it }) {
                return false
            }
            spare += output.amount
            generated += output.amount
        }

        if (spare < requested) {
            return false
        }

        spare -= requested
        return true
    }
}
