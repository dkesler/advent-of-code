package d19

abstract class Rule(val ruleNum: Int) {
    abstract fun match(rules: Map<Int, Rule>, msg: String): Set<Int>
    }

class LiteralRule(ruleNum: Int, val literal: Char) : Rule(ruleNum) {
    override fun match(rules: Map<Int, Rule>, msg: String): Set<Int> {
        if ( msg.startsWith(literal)) {
            return setOf(1)
        } else {
            return setOf()
        }
    }
}

fun andMatch(rules: Map<Int, Rule>, ruleNums: List<Int>, msg: String): Set<Int> {
    if (msg.length == 0) {
        return setOf()
    }

    var msgIdxes = setOf<Int>(0)
    for (ruleNum in ruleNums) {
        val newMsgIdxes = mutableSetOf<Int>()
        for (msgIdx in msgIdxes) {
            val matches = rules.getValue(ruleNum).match(rules, msg.substring(msgIdx))
            newMsgIdxes.addAll(matches.map{it + msgIdx})
        }

        msgIdxes = newMsgIdxes
    }

    return msgIdxes
}


class ListRule(ruleNum: Int, val subrules: List<Int>) : Rule(ruleNum) {
    override fun match(rules: Map<Int, Rule>, msg: String): Set<Int> {
        return andMatch(rules, subrules, msg)
    }
}

class OrRule(ruleNum: Int, val left: List<Int>, val right: List<Int>) : Rule(ruleNum) {
    override fun match(rules: Map<Int, Rule>, msg: String): Set<Int> {
        val leftMatch = andMatch(rules, left, msg)
        val rightMatch = andMatch(rules, right, msg)

        return leftMatch + rightMatch
    }
}

fun toRule(line: String): Rule {
    val (ruleNum, rule) = line.split(":")
    if (rule.contains("\"")) {
        return LiteralRule(ruleNum.toInt(), rule.split("\"")[1][0])
    }

    if (rule.contains("|")) {
        val (left, right) = rule.trim().split("|")
        return OrRule(
            ruleNum.toInt(),
            left.trim().split(" ").map{it.toInt()},
            right.trim().split(" ").map{it.toInt()}
        )
    }

    return ListRule(
        ruleNum.toInt(),
        rule.trim().split(" ").map{it.toInt()}
    )
}



fun main() {
    val content = Class::class.java.getResource("/d19/d19p2").readText()
    val list = content.split("\n").filter{it != ""}

    val rules = list.subList(0, 135).map(::toRule).map{ Pair(it.ruleNum, it) }.toMap()

    val msgs = list.subList(135, list.size)

   println(msgs.filter{ rules.getValue(0).match(rules, it).contains(it.length)}.count())





}
