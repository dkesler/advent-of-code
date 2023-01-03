package d21

import utils.readList


fun main() {
    val lines = readList("/d21.txt")

    val monkeys = mutableMapOf<String, () -> Long>()

    for (line in lines) {
        val s = line.split(":")
        if (s[1].contains("*")) {
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
            monkeys[s[0]] = { monkeys[calc[0].trim()]!!.invoke() - monkeys[calc[1].trim()]!!.invoke()}
        } else {
            monkeys[s[0]] = { s[1].trim().toLong() }
        }
    }

    println(monkeys["root"]!!.invoke())
}