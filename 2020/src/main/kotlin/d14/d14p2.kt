package d14



fun main() {
    val content = Class::class.java.getResource("/d14/d14p1").readText()
    val list = content.split("\n").filter{it != ""}

    var mask = "000000000000000000000000000000000000"
    val mem = mutableMapOf<Long, Long>()
    for (cmd in list) {
        if (cmd.startsWith("mask")) {
            mask = cmd.split(" = ")[1]
        } else {
            val splitCmd = cmd.split(" = ")
            val loc = Regex("mem\\[([0-9]+)\\]").matchEntire(splitCmd[0])!!.groupValues[1].toLong()
            val value = splitCmd[1].toLong()

            val maskedLoc = applyMask2(mask, loc)
            maskedLoc.forEach{ mem[it] = value }
        }
    }

    println(mem.values.sum())

}

fun applyMask2(mask: String, value: Long): List<Long> {
    val vStr = value.toString(2).padStart(36, '0').toCharArray()
    for (i in mask.indices) {
        if (mask[i] != '0') {
            vStr[i] = mask[i]
        }
    }

    return explodeFirst(String(vStr))
}

fun explodeFirst(floatingStr: String): List<Long> {
    if (!floatingStr.contains("X")) {
        return listOf(floatingStr.toLong(2))
    }

    return explodeFirst(floatingStr.replaceFirst("X", "0")) +
            explodeFirst(floatingStr.replaceFirst("X", "1"))
}

