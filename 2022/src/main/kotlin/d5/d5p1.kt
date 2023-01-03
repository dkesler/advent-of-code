package d5

import utils.*

data class Move(val from: Int, val to: Int, val num: Int)

fun main() {
    val stacks = readList("/d5.txt")
            .map{ it.split("").filter{it != ""}.toMutableList() }
            .toMutableList()

    val instr = readList("/d5p2.txt").map {
        val split = it.split(" ")
        Move(split[5].toInt()-1, split[3].toInt()-1, split[1].toInt())
    }

    for (inst in instr) {
        stacks[inst.from].addAll(0, stacks[inst.to].take(inst.num)/*.reversed()*/)
        stacks[inst.to] = stacks[inst.to].subList(inst.num, stacks[inst.to].size)
    }

    println(stacks)
}