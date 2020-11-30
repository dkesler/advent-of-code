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

    fun run(i: Long, computer: Computer): Boolean {
        val inputs = packetsToInput(i)
        val output = computer.runBlockingProgram(inputs)
        outputToPackets(output.second)

        //true if idle
        return queues.getValue(255L).isNotEmpty() && inputs.size == 1 && output.second.size == 0
    }

    //bootstrap with address
    computers.forEach{ i, computer -> outputToPackets(computer.runBlockingProgram(listOf(i)).second) }
    val deliveredToZero = mutableSetOf<Long>()
    do {
        do {
            val idle = computers.asIterable().fold( true, {acc, i -> run(i.key, i.value) && acc })
        } while(!idle)

        val resetPacket = queues.getValue(255L).last()
        queues.getValue(255L).clear()
        queues.getValue(0L).add(resetPacket)
        if (deliveredToZero.contains(resetPacket.y)) {
            println(resetPacket)
        } else {
            deliveredToZero.add(resetPacket.y)
        }
    } while (true)
}

