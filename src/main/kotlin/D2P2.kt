import intcode.runProgram
import java.io.File
import java.lang.System.exit

fun main() {

    val program = File("src/main/resources/d2p1").readText()
        .dropLastWhile { it == '\n' }
        .split(',')
        .map { it.toLong() }

    for (noun in 0..99L) {
        for (verb in 0..99L) {
            val candidate = program.toMutableList()
            candidate.set(1, noun)
            candidate.set(2, verb)
            val result = runProgram(candidate)
            if (candidate[0] == 19690720L) {
                println("Found noun and verb: $noun, $verb")
                exit(0)
            }
        }
    }
}
