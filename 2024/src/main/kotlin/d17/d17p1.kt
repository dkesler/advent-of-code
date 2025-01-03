package d17

import utils.*
import java.lang.Math.pow
import kotlin.math.pow

fun main() {
    var a = 45483412L
    var b = 0L
    var c = 0L

    val program = "2,4,1,3,7,5,0,3,4,1,1,5,5,5,3,0".split(",").filter{it.isNotBlank()}.map{it.toLong() }
    val out = mutableListOf<Long>()
    var iptr = 0
    while(iptr >= 0 && iptr < program.size - 1) {
        when(program[iptr]) {
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
                    iptr = program[iptr+1].toInt()
                }
            }
            4L -> {
                b = b.xor(c)
                iptr += 2
            }
            5L -> {
                out.add( combo(program[iptr+1], a, b, c) % 8 )
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

    println(out)


}

fun combo(i: Long, a: Long, b: Long, c: Long): Long {
    if (i <= 3L) return i
    if (i == 4L) return a
    if (i == 5L) return b
    if (i == 6L) return c
    throw Error("wat")
}
