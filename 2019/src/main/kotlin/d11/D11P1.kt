package d11

import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val panels = Panels()
    val computer = Computer(loadProgramFromFile("2019/src/main/resources/d11/d11p1"))

    do {
        val output = computer.runBlockingProgram(panels.getInput())
        panels.handleOutput(output.second)

    } while (output.first)

    println(panels.everPainted.size)
}

class Panels(
    var x: Int = 0,
    var y: Int = 0,
    var facing: Int = 0, //up, right, down, left
    val whitePanels: MutableSet<Pair<Int, Int>> = mutableSetOf(),
    val everPainted: MutableSet<Pair<Int, Int>> = mutableSetOf()
) {
    fun getInput(): List<Long> {
        if (whitePanels.contains(Pair(x, y))) {
            return listOf(1L)
        } else {
            return listOf(0L)
        }
    }

    fun handleOutput(output: List<Long>) {
        if (output[0] == 0L) {
            whitePanels.remove(Pair(x, y))
        } else {
            whitePanels.add(Pair(x, y))
        }

        everPainted.add(Pair(x, y))

        if (output[1] == 0L) {
            facing = facing - 1
            if (facing == -1) facing = 3
        } else {
            facing = facing + 1
            if (facing == 4) facing = 0
        }

        when(facing) {
            0 -> y -= 1
            1 -> x += 1
            2 -> y += 1
            3 -> x -= 1
        }
    }
}
