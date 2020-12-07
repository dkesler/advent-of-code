package d7

fun toContentPair(line: String): Pair<String, Int> {
    val regex = Regex("([0-9]+) ([a-z ]+).*")
    val match = regex.matchEntire(line)
    return Pair(match!!.groupValues[2].trim(), Integer.parseInt(match!!.groupValues[1]))
}

fun toRule(line: String): Rule {
    val (bag, contents) = line.replace("bags", "")
        .replace("bag", "")
        .split("contain")
    if (contents.contains("no other")) {
        return Rule(bag.trim(), listOf())
    } else {
        val parsedContents = contents.split(", ").map(String::trim).map(::toContentPair)
        return Rule(bag.trim(), parsedContents)
    }
}

data class Rule(val bag: String, val contains: List<Pair<String, Int>>)

fun findOuters(bag: String, map: Map<String, List<String>>): List<String> {
    val searched = mutableSetOf<String>()
//    val outers = mutableSetOf<String>()
    val toSearch = mutableListOf<String>(bag)

    while(!toSearch.isEmpty()) {
        if (map.containsKey(toSearch[0])) {
            val next = map.getValue(toSearch[0]).filter{ !searched.contains(it) }
            searched.addAll(next)
            toSearch.addAll(next)
        }

        toSearch.removeAt(0)
    }

    return searched.toList()
}


fun main() {
    val content = Class::class.java.getResource("/d7/d7p1").readText()
    val list = content.split("\n").filter{it != ""}

    val rules = list.map(::toRule)

    println(rules)

    val invertedMap = rules.flatMap { rule -> rule.contains.map{ c -> Pair(c.first,  rule.bag) } }
        .groupBy { it.first }
        .mapValues { it.value.map {p-> p.second} }

    println(invertedMap)

    val outers = findOuters("shiny gold", invertedMap)
    println(outers)
    println(outers.count())




}

