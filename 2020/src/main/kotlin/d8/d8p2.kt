package d8

fun tryRun(program: Map<Int, Instruction>): Pair<Boolean, Int> {
    val visited = mutableSetOf<Int>()
    var acc = 0
    var iptr = 0

    while (!visited.contains(iptr) && program.containsKey(iptr)) {
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

    return Pair(!program.containsKey(iptr), acc)
}

fun main() {
    val content = Class::class.java.getResource("/d8/d8p1").readText()
    val list = content.split("\n").filter{it != ""}

    val program = list.withIndex().map{ Pair(it.index, toInstruction(it.value))}.toMap()

    for (i in 0..program.size-1) {
        val modifiedProgram = program + Pair(i, program.getValue(i).flipped())
        val (successful, acc) = tryRun(modifiedProgram)
        if (successful) {
            println(acc)
        }
    }

}
