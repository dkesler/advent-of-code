package d2

import intcode.loadProgramFromFile
import intcode.runProgram

fun main() {
    val program = loadProgramFromFile("2019/src/main/resources/d2/d2p1")

    program.set(1, 12)
    program.set(2, 2)

    val outputs = runProgram(program)
    println(program)
}
