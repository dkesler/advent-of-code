package d11

import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val panels = Panels(whitePanels = mutableSetOf(Pair(0, 0)))
    val computer = Computer(loadProgramFromFile("2019/src/main/resources/d11/d11p1"))

    do {
        val output = computer.runBlockingProgram(panels.getInput())
        panels.handleOutput(output.second)

    } while (output.first)

    val minX = panels.whitePanels.map{ it.first }.min()!!.or(0)
    val maxX: Int = panels.whitePanels.map{ it.first }.max()!!.or(0)
    val minY = panels.whitePanels.map{ it.second }.min()!!.or(0)
    val maxY = panels.whitePanels.map{ it.second }.max()!!.or(0)

    minY.rangeTo(maxY).forEach {
        y -> minX.rangeTo(maxX).forEach {
            x -> if (panels.whitePanels.contains(Pair(x, y))) { print("#") } else { print(" ") }


    }
        println("")
    }
}
