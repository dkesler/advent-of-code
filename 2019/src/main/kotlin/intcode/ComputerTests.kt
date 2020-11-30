package intcode

fun main() {

    if (runProgramFromFile("2019/src/main/resources/intcode/d5p1", listOf(1)) != listOf(0L, 0, 0, 0, 0, 0, 0, 0, 0, 7988899)) {
        println("Failed p5p1")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/immediatejumptest", listOf(0)) != listOf(0L)) {
        println("failed immediate jump test 0")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/immediatejumptest", listOf(20)) != listOf(1L)) {
        println("failed immediate jump test > 0")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/positionjumptest", listOf(0)) != listOf(0L)) {
        println("failed position jump test 0")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/positionjumptest", listOf(20)) != listOf(1L)) {
        println("failed position jump test > 0")
    }

    if (runProgram(mutableListOf(3,9,8,9,10,9,4,9,99,-1,8), listOf(8)) != listOf(1L)) {
        println("failed position == 8 test")
    }

    if (runProgram(mutableListOf(3,9,8,9,10,9,4,9,99,-1,8), listOf(9)) != listOf(0L)) {
        println("failed position != 8 test")
    }

    if (runProgram(mutableListOf(3,9,7,9,10,9,4,9,99,-1,8), listOf(7)) != listOf(1L)) {
        println("failed position < 8 test")
    }

    if (runProgram(mutableListOf(3,9,7,9,10,9,4,9,99,-1,8), listOf(9)) != listOf(0L)) {
        println("failed position not < 8 test")
    }

    if (runProgram(mutableListOf(3,3,1108,-1,8,3,4,3,99), listOf(8)) != listOf(1L)) {
        println("failed immediate == 8 test")
    }

    if (runProgram(mutableListOf(3,3,1108,-1,8,3,4,3,99), listOf(9)) != listOf(0L)) {
        println("failed immediate != 8 test")
    }

    if (runProgram(mutableListOf(3,3,1107,-1,8,3,4,3,99), listOf(7)) != listOf(1L)) {
        println("failed immediate < 8 test")
    }

    if (runProgram(mutableListOf(3,3,1107,-1,8,3,4,3,99), listOf(9)) != listOf(0L)) {
        println("failed immediate not < 8 test")
    }


    if (runProgramFromFile("2019/src/main/resources/intcode/iftest", listOf(6)) != listOf(999L)) {
        println("failed iftest < 8")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/iftest", listOf(8)) != listOf(1000L)) {
        println("failed iftest == 8")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/iftest", listOf(10)) != listOf(1001L)) {
        println("failed iftest > 8")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/quine", listOf()) != loadProgramFromFile("2019/src/main/resources/intcode/quine") ) {
        println("failed quine")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/16digit", listOf()) != listOf(1219070632396864L) ) {
        println("failed 16digit ")
    }

    if (runProgramFromFile("2019/src/main/resources/intcode/largeoutput", listOf()) != listOf(1125899906842624L) ) {
        println("failed largeoutput")
    }

}
