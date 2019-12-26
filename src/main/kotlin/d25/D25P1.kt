package d25

import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val computer = Computer(loadProgramFromFile("src/main/resources/d25/d25p1"))
    val initialOutput = computer.runBlockingProgram(listOf())
    initialOutput.second.forEach { print(it.toChar()) }
    println("")

    do {
        val input = readLine() + "\n"

        val output = computer.runBlockingProgram(input.map{it.toLong()})
        output.second.forEach { print(it.toChar()) }
        println("")

    } while(output.first)
}

/*
                                   sci lab
                                   tambourine
                                     |
           observatory             holodeck
           cake                    lava
             |                       |
navig    - hot choc- sick bay -      corr
esc pod    gnt elec  inf loop
                          |            |
storage -       stables - gw center    passages - engineering
klein           spool                  shell
|                |        |                          |
hallway         warp     kitchen                  arcade
w. mach                   mug                      photons
|                 |
breach -crew      security -
        antenna
 */
