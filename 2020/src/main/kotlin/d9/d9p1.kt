package d9


fun main() {
    val content = Class::class.java.getResource("/d9/d9p1").readText()
    val list = content.split("\n").filter{it != ""}.map(String::toLong)
    println(list)
    val buffer = 25
    val index = (buffer..list.size-1).find { isNotPrevSum(list.subList(it-buffer, it), list.get(it)) }
    println(index)
    println(list[index!!])



}

fun isNotPrevSum(subList: List<Long>, toCheck: Long): Boolean {
    for(i in subList.indices) {
        for(j in i+1..subList.size-1) {
            if (subList[i] != subList[j] && subList[i] + subList[j] == toCheck) {
                return false
            }
        }
    }

    return true
}
