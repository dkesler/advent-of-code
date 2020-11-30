package d23

import intcode.Computer
import intcode.loadProgramFromFile

fun main() {
    val computers = (0..49L).map{ Pair(it, Computer(loadProgramFromFile("2019/src/main/resources/d23/d23p1"))) }.toMap()
    val queues = (0..49L).map{ Pair(it, mutableListOf<Packet>()) }.toMap().plus(Pair(255L, mutableListOf<Packet>()))

    fun packetsToInput(i: Long): List<Long> {
        val packets = queues.getValue(i).toList()
        queues.getValue(i).clear()
        if (packets.isEmpty()) {
            return listOf(-1L)
        } else {
            return packets.map{listOf(it.x, it.y)}.flatten()
        }
    }

    fun outputToPackets(output: List<Long>) {
        output.chunked(3).forEach{ queues.getValue(it[0]).add(Packet(it[1], it[2])) }
    }

    fun run(i: Long, computer: Computer) {
        val output = computer.runBlockingProgram(packetsToInput(i))
        outputToPackets(output.second)
    }

    //bootstrap with address
    computers.forEach{ i, computer -> outputToPackets(computer.runBlockingProgram(listOf(i)).second) }

    while(queues.getValue(255).isEmpty()) {
        computers.forEach(::run)
    }

    println(queues.getValue(255))
}

data class Packet(val x: Long, val y: Long)

