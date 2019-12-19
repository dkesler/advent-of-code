import intcode.Computer
import intcode.loadProgramFromFile
import intcode.runProgramFromFile

fun main() {
    val computer = Computer(loadProgramFromFile("src/main/resources/d19p1"))

    val output = (0L..49).map{x -> (0L..49).map{ listOf(x, it) } }.flatten()
        .map{ runProgramFromFile("src/main/resources/d19p1", it)}

    println(output)

    println(output.filter { it[0] == 1L }.count())
}
