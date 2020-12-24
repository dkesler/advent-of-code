package d23

import java.util.*
import kotlin.collections.LinkedHashSet

fun doMove(active: Int, list: List<Int>): List<Int> {
    val curLabel = list[active]
    var targetLabel = curLabel-1
    if (targetLabel == 0) {
     targetLabel += list.size
    }
    val beingMoved = list.subList(active+1, active+4)
    while(targetLabel in beingMoved) {
        targetLabel -= 1
        if (targetLabel <= 0) {
            targetLabel += list.size
        }
    }

    val targetIdx = list.indexOf(targetLabel)

    return list.subList(active+4, targetIdx+1) +
            beingMoved +
            list.subList(targetIdx+1, list.size) +
            list.subList(active, active+1)
}


data class N(val data: Int, var prev: N?, var next: N?) {
    override fun toString(): String {
        return "data: ${data}, prev: ${prev!!.data}, next: ${next!!.data}"
    }
}

data class LL(var head: N, var tail: N, val map: MutableMap<Int, N>) {
    fun rotateHead(): Unit {
        tail = head
        head = head.next!!
    }
    fun nodeAtLabel(label: Int): N {
        return map.getValue(label)
    }

    fun extractSubList(fromLabel: Int, size: Int): List<Int> {
        val first = map.getValue(fromLabel)

        var next = first.next
        val ret = mutableListOf(next!!.data)
        while(ret.size < size) {
            next = next!!.next
            ret.add(next!!.data)
        }

        first.next = next!!.next
        next.next!!.prev = first

        return ret
    }


    fun insertAtLabel(atLabel: Int, data: Int): Unit {
        val target = map.getValue(atLabel)
        val targetEnd = target.next!!

        val n = N(data, target, targetEnd)
        target.next = n
        targetEnd.prev = n

        map.put(data, n)
    }

}

fun doMove2(cups: LL) {
    val curLabel =  cups.head.data

    var targetLabel = curLabel-1
    if (targetLabel == 0) {
        targetLabel += cups.map.size
    }
    val beingMoved = setOf(
        cups.head.next!!.data,
        cups.head.next!!.next!!.data,
        cups.head.next!!.next!!.next!!.data,
    )
    while(targetLabel in beingMoved) {
        targetLabel -= 1
        if (targetLabel <= 0) {
            targetLabel += cups.map.size
        }
    }

    val extracted = cups.extractSubList(curLabel, 3)
    extracted.reversed().forEach { cups.insertAtLabel(targetLabel, it) }

    cups.rotateHead()
}


fun main() {
    val content = Class::class.java.getResource("/d23/d23p1").readText()

    var list = "523764819".map{it.toString().toInt()} + (10..1000000)
    //var list = "389125467".map{it.toString().toInt()} + (10..1000000)

    val nodes = list.map{ N(it, null, null) }
    for (i in nodes.indices) {
        var prevIdx = if (i == 0) nodes.size-1 else i-1
        var nextIdx = if (i == nodes.size-1) 0 else i+1
        nodes[i].prev = nodes[prevIdx]
        nodes[i].next = nodes[nextIdx]
    }

    val nodeMap = nodes.map{ Pair(it.data, it) }.toMap()
    val cups = LL(nodeMap.getValue(list.get(0)), nodeMap.getValue(1000000), nodeMap.toMutableMap())


    for (i in 0 until 10000000) {
        doMove2(cups)
        if (i % 100000 == 0) {
            println(i)
        }
    }

    val n1 = cups.nodeAtLabel(1)
    println(n1.next!!.data.toLong() * n1.next!!.next!!.data.toLong())
}
