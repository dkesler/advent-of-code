package d16

fun toRule(line: String): Rule {
    val field = line.split(":")[0]
    val ranges = line.split(":")[1].split(" or ")
    return Rule(
        field,
        ranges.map{ str ->
            val split = str.split("-")
            Pair(split[0].trim().toLong(), split[1].trim().toLong())
        }
    )
}


data class Rule(val field: String, val valid: List<Pair<Long, Long>>) {
    fun isValidValue(v: Long): Boolean {
        return valid.any { v >= it.first && v <= it.second }
    }
}

fun matchesAnyRule(v: Long, rules: List<Rule>): Boolean {
    return rules.any{ it.isValidValue(v) }
}

fun main() {
    val content = Class::class.java.getResource("/d16/d16p1").readText()
    val list = content.split("\n").filter{it != ""}

    val rules = list.subList(0, 20).map(::toRule)

    val myTicket = list[21].split(",").map{it.trim().toLong()}

    val nearbyTickets = list.subList(23, list.size).map{
        it.split(",").map{it.trim().toLong()}
    }

    println(nearbyTickets.flatMap { it }.filter{ !matchesAnyRule(it, rules) }.sum())


    val validNearbyTickets = nearbyTickets.filter{ it.all { matchesAnyRule(it, rules) } }

    val fieldMap = mutableMapOf<Int, Rule>()

    while(fieldMap.keys.size < rules.size) {
        for (rule in rules) {
            val matchingFields = mutableSetOf<Int>()
            for (i in 0..rules.size-1) {
                if (!fieldMap.containsKey(i)) {
                    if (allTicketsMatch(validNearbyTickets, rule, i)) {
                        matchingFields.add(i)
                    }
                }
            }
            if (matchingFields.size == 1) {
                fieldMap.put(matchingFields.toList()[0], rule)
            }
        }
    }

    println(fieldMap)

    val relevantFields = fieldMap.filter{ it.value.field.startsWith("departure") }.map{it.key}

    var sum = 1L
    for (fieldIdx in relevantFields) {
        sum *= myTicket[fieldIdx]
    }

    println(sum)


}

fun allTicketsMatch(tickets: List<List<Long>>, rule: Rule, fieldIdx: Int): Boolean {
    return tickets.all{ ticketMatches(it, rule, fieldIdx) }
}

fun ticketMatches(ticket: List<Long>, rule: Rule, fieldIdx: Int): Boolean {
    return rule.isValidValue(ticket[fieldIdx])
}
