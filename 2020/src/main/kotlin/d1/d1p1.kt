package d1


fun main() {
    val content = Class::class.java.getResource("/d1/d1p1").readText()
    val list = content.split("\n").filter{it != ""}.map{Integer.parseInt(it)}
    val pair = findSum(list)
    println(pair)
    println(pair.first * pair.second)
}


fun findSum(xs: List<Int>): Pair<Int, Int> {
    for (i in xs.indices) {
       for (j in i until xs.size) {
           if (xs.get(i) + xs.get(j) == 2020) {
               return Pair(xs.get(i), xs.get(j))
           }
       }
    }
    return Pair(0, 0)
}
