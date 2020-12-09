package d9

fun main() {
    val content = Class::class.java.getResource("/d9/d9p1").readText()
    val list = content.split("\n").filter{it != ""}.map(String::toLong)

    val target = 556543474L
    //val target = 127L

    val group = findSet(target, list)

    println(group)
    println(group.min()!! + group.max()!!)


}

fun findSet(target: Long, list: List<Long>) : List<Long>{
    var acc = 0L
    val group = mutableListOf<Long>()
    for(i in list) {
        while(acc > target && group.isNotEmpty()) {
            acc -= group[0]
            group.removeAt(0)
        }

        if (acc == target) {
            return group
        }

        acc += i
        group.add(i)
    }

    return group.toList()

}

