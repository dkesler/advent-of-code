package d17

import kotlin.math.pow

fun main() {
    val program = "2,4,1,3,7,5,0,3,4,1,1,5,5,5,3,0".split(",").filter { it.isNotBlank() }.map { it.toLong() }

    println(run(program, 45483412L))

    //This program loops while a > 0, dividing a by 8 every iteration.  Each iteration outputs one value, solely
    //derived from the value of a % 8 at the beginning of the iteration.  Thus each iteration is completely independent
    //and can be solved for in isolation.  The solution below starts by finding the value the value of a that outputs solely the
    //last value of the program by starting with a=0 and incrementing by one each attempt.  The value of a that finds the last
    //instruction is multiplied by 8, then used as the starting point for a search to find the starting value of a
    //which will output the last two instructions.  This process is repeated until all instructions are output.

    //b = a % 8
    //b = b xor 3
    //c = a / 2^b
    //a = a / 2^3
    //b = b xor c
    //b = b xor 5
    //out b
    //jnz(a, 0)

    println(findAMatchingOutput(program, program))
}

private fun findAMatchingOutput(program: List<Long>, target: List<Long>): Long {
    var aStart = if (target.size == 1) {
        0
    } else {
        8 * findAMatchingOutput(program, target.subList(1, target.size))
    }

    while( run(program, aStart) != target) {
        aStart++
    }

    return aStart
}

private fun run(program: List<Long>, aStart: Long): MutableList<Long> {
    var a = aStart
    var b = 0L
    var c = 0L

    val out = mutableListOf<Long>()
    var iptr = 0
    while (iptr >= 0 && iptr < program.size - 1) {
        when (program[iptr]) {
            0L -> {
                a = (a / 2.0.pow(combo(program[iptr + 1], a, b, c).toDouble())).toLong()
                iptr += 2
            }

            1L -> {
                b = b.xor(program[iptr + 1])
                iptr += 2
            }

            2L -> {
                b = combo(program[iptr + 1], a, b, c) % 8
                iptr += 2
            }

            3L -> {
                if (a == 0L) {
                    iptr += 2
                } else {
                    iptr = program[iptr + 1].toInt()
                }
            }

            4L -> {
                b = b.xor(c)
                iptr += 2
            }

            5L -> {
                val outval = combo(program[iptr + 1], a, b, c) % 8
                out.add(outval)
                iptr += 2
            }

            6L -> {
                b = (a / 2.0.pow(combo(program[iptr + 1], a, b, c).toDouble())).toLong()
                iptr += 2
            }

            7L -> {
                c = (a / 2.0.pow(combo(program[iptr + 1], a, b, c).toDouble())).toLong()
                iptr += 2
            }
        }
    }
    return out
}