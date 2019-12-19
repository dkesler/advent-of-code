import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val computer = Computer(loadProgramFromFile("src/main/resources/d17p2"))

    val inputs = "A,B,B,A,C,B,C,C,B,A\nR,10,R,8,L,10,L,10\nR,8,L,6,L,6\nL,10,R,10,L,6\nn\n".map { it.toLong() }
    val output = computer.runBlockingProgram(inputs).second

    println(output)
}
