package d8

fun toInstruction(line: String): Instruction {
    val (cmd, value) = line.split(" ");
    return Instruction(cmd, Integer.parseInt(value))
}

data class Instruction(val cmd: String, val value: Int) {
    fun flipped(): Instruction {
        if (cmd == "jmp") {
            return Instruction("nop", value)
        } else if (cmd == "nop") {
            return Instruction("acc", value)
        } else {
            return this
        }
    }
}

fun main() {
    val content = Class::class.java.getResource("/d8/d8p1").readText()
    val list = content.split("\n").filter{it != ""}

    val program = list.withIndex().map{ Pair(it.index, toInstruction(it.value))}.toMap()

    val visited = mutableSetOf<Int>()
    var acc = 0
    var iptr = 0

    while (!visited.contains(iptr)) {
        println(iptr.toString() + " " + acc)

        visited.add(iptr)
        val instruction = program.getValue(iptr)
        if (instruction.cmd == "acc") {
            acc += instruction.value
            iptr += 1
        } else if (instruction.cmd == "jmp") {
            iptr += instruction.value
        } else {
            iptr += 1
        }
    }

    println(acc)



}
