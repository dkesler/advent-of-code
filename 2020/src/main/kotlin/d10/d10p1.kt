package d10

fun main() {
    val content = Class::class.java.getResource("/d10/d10p1").readText()
    val list = content.split("\n").filter{it != ""}.map(String::toLong).sorted()

    println(list)

    val diffs = list.subList(1, list.size).mapIndexed( { i: Int, v: Long ->
        v - list[i]
    })

    println(diffs)
    val oneDiffCount = diffs.filter { it == 1L }.count()
    val threeDiffCount = diffs.filter { it == 3L }.count()
    println(oneDiffCount)
    println(threeDiffCount)
    println(oneDiffCount * (threeDiffCount+1))



}
