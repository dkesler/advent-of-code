import intcode.runProgramFromFile

fun main() {
    val size = 400L
    val offset = 800L


    val output = (offset..(offset+size)).map{y -> (offset..(offset+size)).map{ listOf(it, y) } }.flatten()
        .map{
            val output = runProgramFromFile("src/main/resources/d19p1", it)
            //println("in: $it, out: $output")
            output
        }
    //10x10
    //84, 105

    println(runProgramFromFile("src/main/resources/d19p1", listOf(815, 1016)))
    println(runProgramFromFile("src/main/resources/d19p1", listOf(915, 1016)))
    println(runProgramFromFile("src/main/resources/d19p1", listOf(815, 1116)))

    output.chunked(size.toInt()+1).forEachIndexed{ y, line ->
        line.forEach{ print(it[0]) }
        println("")
    }
}
