package d1


fun main() {
    val content = Class::class.java.getResource("/d1/d1p1").readText()
    val list = content.split("\n").filter{it != ""}.map{Integer.parseInt(it)}
    val triple = findSum2(list)
    println(triple)
    println(triple.first * triple.second * triple.third)
}


fun findSum2(xs: List<Int>): Triple<Int, Int, Int> {
    for (i in xs.indices) {
       for (j in i until xs.size) {
           for (k in j until xs.size) {
               if (xs.get(i) + xs.get(j) + xs.get(k) == 2020) {
                   return Triple(xs.get(i), xs.get(j), xs.get(k))
               }
           }
       }
    }
    return Triple(0, 0,0)
}
