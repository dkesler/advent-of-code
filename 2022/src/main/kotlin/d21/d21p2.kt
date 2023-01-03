package d21

import utils.readList


fun main() {
    val lines = readList("/d21.txt")

    val monkeys = mutableMapOf<String, () -> Long>()

    for (line in lines) {
        val s = line.split(":")
        if (s[0].trim() == "root") {
            val calc = s[1].split("+")
            monkeys["root"] = {
                val l = monkeys[calc[0].trim()]!!.invoke()
                val r = monkeys[calc[1].trim()]!!.invoke()
                l - r
            }
        } else if (s[1].contains("*")) {
            val calc = s[1].split("*")
            monkeys[s[0]] = { monkeys[calc[0].trim()]!!.invoke() * monkeys[calc[1].trim()]!!.invoke()}
        } else if (s[1].contains("/")) {
            val calc = s[1].split("/")
            monkeys[s[0]] = { monkeys[calc[0].trim()]!!.invoke() / monkeys[calc[1].trim()]!!.invoke()}
        } else  if (s[1].contains("+")) {
            val calc = s[1].split("+")
            monkeys[s[0]] = { monkeys[calc[0].trim()]!!.invoke() + monkeys[calc[1].trim()]!!.invoke()}
        } else if (s[1].contains("-")) {
            val calc = s[1].split("-")
            monkeys[s[0]] = { monkeys[calc[0].trim()]!!.invoke() - monkeys[calc[1].trim()]!!.invoke() }
        } else {
            monkeys[s[0]] = { s[1].trim().toLong() }
        }
    }

    var humn = 10000L
    var maxHumn: Long? = null
    var minHumn = humn
    monkeys["humn"] = { 10000 }
    var diff = monkeys["root"]!!.invoke()
    while(diff != 0L) {
        if (diff > 0) {
            minHumn = humn
            if (maxHumn == null) {
                humn *= 2
            } else {
                humn = (humn + maxHumn) / 2
            }
        } else {
            maxHumn = humn
            humn = (humn + minHumn) /2
        }

        monkeys["humn"] = { humn }
        diff = monkeys["root"]!!.invoke()
        println(diff)
    }

    println(humn)

}