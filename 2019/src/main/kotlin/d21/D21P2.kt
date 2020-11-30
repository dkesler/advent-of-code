package d21

import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val computer = Computer(loadProgramFromFile("2019/src/main/resources/d21/d21p1"))

    //if land in 4 and not land in 1 or 2 or 3
    //and land in 5 OR land in 8

    val input = "NOT A T\n" +
            "OR T J\n" +
            "NOT B T\n" +
            "OR T J\n" +
            "NOT C T\n" +
            "OR T J\n" +
            "AND D J\n" +
            "NOT J T\n" + //if J is 1, it puts 0 into T so E and H can be ORd properly.  if J is 0, the later AND T J does nothing so this doesnt matter
            "OR E T\n" +
            "OR H T\n" +
            "AND T J\n" +
            "RUN\n"

    val output = computer.runBlockingProgram(input.map{it.toLong()})

    output.second.map{it.toChar()}.forEach(::print)
    println(output.second)

}
