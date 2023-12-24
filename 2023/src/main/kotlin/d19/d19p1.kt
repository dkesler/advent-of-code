package d19

import utils.*

data class Part(val x: Int, val m: Int, val a: Int, val s: Int)
fun main() {
    val blocks = readBlocks("/d19.txt")

    val workflows = blocks[0].associate {
        val s = it.split("{")
        val name = s[0]
        val rules = s[1].substring(0, s[1].length-1).split(",")
        Pair(name, rules)
    }

    val parts = blocks[1].map {
        val s = it.substring(1, it.length-1).split(',')
        Part(
                s[0].split("=")[1].toInt(),
                s[1].split("=")[1].toInt(),
                s[2].split("=")[1].toInt(),
                s[3].split("=")[1].toInt()
        )
    }

    val accepted = mutableSetOf<Part>()

    for (part in parts) {
        var next = "in"
        while(next != "A" && next != "R") {
            val wf = workflows[next]!!

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
                        if (value > target) {
                            next = s[1]
                            break
                        }
                    } else {
                        if (value < target) {
                            next = s[1]
                            break
                        }
                    }
                } else {
                    next = rule
                }
            }

        }

        if (next == "A") accepted.add(part)
    }

    println(
            accepted.map{ it.a + it.x + it.s + it.m}.sum()
    )
}