package d25


fun loopSize(pubKey: Long): Long {
    var loops = 0L
    var cur = 7L
    while( cur != pubKey ) {
        cur *= 7
        cur = cur % 20201227
        loops++
    }
    return loops
}

fun transform(subject: Long, loops: Long): Long {
    var cur = subject
    for (i in 0 until loops) {
        cur *= subject
        cur = cur % 20201227
    }
    return cur
}

fun main() {
    val content = Class::class.java.getResource("/d25/d25p1").readText()
    //val list = content.split("\n").filter{it != ""}

    val pubKey1 = 8987316L
    val pubKey2 = 14681524L

    println(loopSize(pubKey1))
    println(loopSize(pubKey2))

    val loops1 = loopSize(pubKey1)
    val loops2 = loopSize(pubKey2)

    println(transform(pubKey1, loops2))
    println(transform(pubKey2, loops1))

}
