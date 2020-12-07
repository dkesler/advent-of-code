package d7

fun bagCount(bag: String, rules: Map<String, Rule>, memo: Map<String, Int>): Map<String, Int> {
    if (memo.containsKey(bag)) {
        return memo
    }

    val newMemo = rules.getValue(bag).contains.map{it.first}.fold(
        memo,
        { acc,  subBag -> bagCount(subBag, rules, acc) }
    )

    val count = rules.getValue(bag).contains.map{ it.second * newMemo.getValue(it.first) }.sum() + 1

    return newMemo + Pair(bag, count)
}

fun main() {
    val content = Class::class.java.getResource("/d7/d7p1").readText()
    val list = content.split("\n").filter{it != ""}

    val rules = list.map(::toRule)

    val rulesByBag = rules.map{ Pair(it.bag, it) }.toMap()

    val bagCounts = bagCount("shiny gold", rulesByBag, mapOf())
    println(bagCounts)

    println(bagCounts.getValue("shiny gold")-1)
}
