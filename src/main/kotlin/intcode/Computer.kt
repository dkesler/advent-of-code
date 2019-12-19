package intcode

import java.io.File

fun runProgram(program: MutableList<Long>): List<Long> {
    return runProgram(program, listOf())
}

private val validOpcodes: Set<Int> = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

class Computer(val program: MutableList<Long>) {
    var position = 0
    var relativeBase = 0
    fun runBlockingProgram(inputs: List<Long>): Pair<Boolean, List<Long>> {

        var inputIdx = 0
        val outputs = mutableListOf<Long>()

        var fullOpCode = program[position].toInt()
        var decodedOpCode = (fullOpCode % 100).toInt()

        fun expandMemory(value: Long) {
            while (program.size <= value+4) {
                program.add(0)
            }
        }

        fun setMem(idx: Long, value: Long) {
            expandMemory(idx)
            program.set(idx.toInt(), value)
        }

        fun addrLookup(opNum: Int): Long {
            val value = program[position + opNum]
            val mode = opMode(fullOpCode, opNum)
            if (mode == 0) {
                return value
            }  else if (mode == 2) {
                return (value + relativeBase)
            } else {
                throw RuntimeException("Unexpected addr operand mode " + mode)
            }
        }

        fun lookup(opNum: Int): Long {
            val value = program[position + opNum]
            val mode = opMode(fullOpCode, opNum)
            if (mode == 0) {
                expandMemory(value)
                return program[value.toInt()]
            } else if (mode == 1) {
                return value
            } else if (mode == 2) {
                expandMemory(value + relativeBase)
                return program[(value + relativeBase).toInt()]
            } else {
                throw RuntimeException("Unexpected operand mode " + mode)
            }
        }

        while (decodedOpCode in validOpcodes) {
            when (decodedOpCode) {
                1 -> {
                    val value = lookup(1) + lookup(2)
                    setMem(addrLookup(3), value)
                    position += 4
                }
                2 -> {
                    setMem(addrLookup(3), lookup(1) * lookup(2))
                    position += 4
                }
                3 -> {
                    if (inputs.size <= inputIdx) {
                        return Pair(true, outputs)
                    }
                    setMem(addrLookup(1), inputs[inputIdx])
                    inputIdx++
                    position += 2
                }
                4 -> {
                    outputs.add(lookup(1))
                    position += 2
                }
                5 -> {
                    if (lookup(1) != 0L) {
                        position = lookup(2).toInt()
                    } else {
                        position += 3
                    }
                }
                6 -> {
                    if (lookup(1) == 0L) {
                        position = lookup(2).toInt()
                    } else {
                        position += 3
                    }
                }
                7 -> {
                    if (lookup(1) < lookup(2)) {
                        setMem(addrLookup(3), 1)
                    } else {
                        setMem(addrLookup(3), 0)
                    }
                    position += 4
                }
                8 -> {
                    if (lookup(1) == lookup(2)) {
                        setMem(addrLookup(3), 1)
                    } else {
                        setMem(addrLookup(3), 0)
                    }
                    position += 4
                }
                9 -> {
                    relativeBase += lookup(1).toInt()
                    position += 2
                }
            }

            fullOpCode = program[position].toInt()
            decodedOpCode = fullOpCode % 100
        }

        if (program[position] != 99L) {
            println("Unexpected opcode " + program[position])
        }

        return Pair(false, outputs)
    }



}


fun runProgram(program: MutableList<Long>, inputs: List<Long>): List<Long> {
    return Computer(program).runBlockingProgram(inputs).second
}

internal fun opMode(opcode: Int, opNum: Int): Int {
    val str = "0000" + opcode.toString()
    return str[str.length - 2 - opNum].toString().toInt()
}

fun runProgramFromFile(filename: String): List<Long> {
    return runProgramFromFile(filename, listOf())
}

fun runProgramFromFile(filename: String, inputs:List<Long>): List<Long> {
    return runProgram(loadProgramFromFile(filename), inputs)
}

fun loadProgramFromFile(filename: String): MutableList<Long> {
    return readProgramFromText(File(filename).readText())
}

fun readProgramFromText(text: String): MutableList<Long> {
    return text.trim()
        .split(",")
        .map { it.toLong() }
        .toMutableList()
}

