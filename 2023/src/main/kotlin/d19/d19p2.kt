package d19

import utils.*

data class PartRange(val x: IntRange, val m: IntRange, val a: IntRange, val s: IntRange)
fun main() {
    val blocks = readBlocks("/d19.txt")

    val workflows = blocks[0].associate {
        val s = it.split("{")
        val name = s[0]
        val rules = s[1].substring(0, s[1].length-1).split(",")
        Pair(name, rules)
    }

    val partRanges = mutableSetOf(Pair("in", PartRange(1..4000, 1..4000, 1..4000, 1..4000)))

    val accepted = mutableSetOf<PartRange>()

    while(partRanges.isNotEmpty()) {
        val next = partRanges.first()
        partRanges.remove(next)

        if (next.first == "A") {
            accepted.add(next.second)
        } else if (next.first != "R") {
            val part = next.second
            if (next.first !in workflows.keys) println(next.first)
            val wf = workflows[next.first]!!
            for (rule in wf) {
                if (':' in rule) {
                    val s = rule.split(':')
                    val value = if (rule.startsWith('x'))
                        part.x
                    else if (rule.startsWith('m'))
                        part.m
                    else if (rule.startsWith('a'))
                        part.a
                    else
                        part.s

                    val target = s[0].substring(2, s[0].length).toInt()
                    if ('>' in rule) {
                        if (value.first > target) {
                            partRanges.add(Pair(s[1], part))
                            break
                        } else if (target+1 in value) {
                            if (rule.startsWith('x')) {
                                val lowPart = PartRange(value.first..target, part.m, part.a, part.s)
                                val highPart = PartRange(target+1..value.last, part.m, part.a, part.s)
                                partRanges.add(Pair(s[1], highPart))
                                partRanges.add(Pair(next.first, lowPart))
                            } else if (rule.startsWith('m')) {
                                val lowPart = PartRange(part.x, value.first..target, part.a, part.s)
                                val highPart = PartRange(part.x, target+1..value.last, part.a, part.s)
                                partRanges.add(Pair(s[1], highPart))
                                partRanges.add(Pair(next.first, lowPart))
                            } else if (rule.startsWith('a')) {
                                val lowPart = PartRange(part.x, part.m, value.first..target, part.s)
                                val highPart = PartRange(part.x, part.m, target+1..value.last, part.s)
                                partRanges.add(Pair(s[1], highPart))
                                partRanges.add(Pair(next.first, lowPart))
                            } else {
                                val lowPart = PartRange(part.x, part.m, part.a, value.first..target)
                                val highPart = PartRange(part.x, part.m, part.a, target+1..value.last)
                                partRanges.add(Pair(s[1], highPart))
                                partRanges.add(Pair(next.first, lowPart))
                            }
                            break
                        }
                    } else {
                        if (value.last < target) {
                            partRanges.add(Pair(s[1], part))
                            break
                        } else if (target-1 in value) {
                            if (rule.startsWith('x')) {
                                val lowPart = PartRange(value.first..target-1, part.m, part.a, part.s)
                                val highPart = PartRange(target..value.last, part.m, part.a, part.s)
                                partRanges.add(Pair(s[1], lowPart))
                                partRanges.add(Pair(next.first, highPart))
                            } else if (rule.startsWith('m')) {
                                val lowPart = PartRange(part.x, value.first..target-1, part.a, part.s)
                                val highPart = PartRange(part.x, target..value.last, part.a, part.s)
                                partRanges.add(Pair(s[1], lowPart))
                                partRanges.add(Pair(next.first, highPart))
                            } else if (rule.startsWith('a')) {
                                val lowPart = PartRange(part.x, part.m, value.first..target-1, part.s)
                                val highPart = PartRange(part.x, part.m, target..value.last, part.s)
                                partRanges.add(Pair(s[1], lowPart))
                                partRanges.add(Pair(next.first, highPart))
                            } else {
                                val lowPart = PartRange(part.x, part.m, part.a, value.first..target-1)
                                val highPart = PartRange(part.x, part.m, part.a, target..value.last)
                                partRanges.add(Pair(s[1], lowPart))
                                partRanges.add(Pair(next.first, highPart))
                            }
                            break
                        }

                    }
                } else {
                    partRanges.add(Pair(rule, part))
                }
            }
        }
    }

    println(
            accepted.map{ (it.a.last.toLong() - it.a.first + 1) * (it.x.last - it.x.first + 1) * (it.s.last - it.s.first +1) * (it.m.last - it.m.first + 1)}.sum()
    )
}