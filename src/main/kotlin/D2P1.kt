import intcode.loadProgramFromFile
import intcode.runProgram

fun main() {
    val program = loadProgramFromFile("src/main/resources/d2p1")

    program.set(1, 12)
    program.set(2, 2)

    val outputs = runProgram(program)
    println(program)
}
