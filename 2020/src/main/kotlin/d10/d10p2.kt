package d10

fun findConnections(adapters: List<Long>): Long {
    val memo = mutableMapOf<Int, Long>()
    fun findConnectionsMemoed(idx: Int): Long {
        if (idx == adapters.size - 1) {
            return 1;
        }

        if (memo.containsKey(idx)) {
            return memo.getValue(idx)
        }

        var connectionCount = 0L
        if (adapters[idx+1] - adapters[idx] <= 3) {
            connectionCount += findConnectionsMemoed(idx+1)
        }
        if (idx+2 < adapters.size && adapters[idx+2] - adapters[idx] <=3) {
            connectionCount += findConnectionsMemoed(idx+2)
        }
        if (idx+3 < adapters.size && adapters[idx+3] - adapters[idx] <=3) {
            connectionCount += findConnectionsMemoed(idx+3)
        }

        memo.put(idx, connectionCount)

        return connectionCount
    }


    return findConnectionsMemoed(0)

}
fun main() {
    val content = Class::class.java.getResource("/d10/d10p1").readText()
    val list = content.split("\n").filter{it != ""}.map(String::toLong).sorted()

    val listWithDevice = list + (list[list.size-1]+3)

    val validConnections = findConnections(listWithDevice)

    println(validConnections)
}
