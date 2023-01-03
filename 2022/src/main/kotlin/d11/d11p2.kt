package d11

import utils.readList

fun main() {
    val monkeys = listOf(
            Monkey(mutableListOf(66, 71, 94), 0, { x -> x * 5 }, { if (it % 3 == 0L) 7 else 4 }),
            Monkey(mutableListOf(70), 0, { it + 6 }, { if (it % 17 == 0L) 3 else 0 }),
            Monkey(mutableListOf(62, 68, 56, 65, 94, 78), 0, { it + 5 }, { if (it % 2 == 0L) 3 else 1 }),
            Monkey(mutableListOf(89, 94, 94, 67), 0, { it + 2 }, { if (it % 19 == 0L) 7 else 0 }),
            Monkey(mutableListOf(71, 61, 73, 65, 98, 98, 63), 0, { it * 7 }, { if (it % 11 == 0L) 5 else 6 }),
            Monkey(mutableListOf(55, 62, 68, 61, 60), 0, { it + 7 }, { if (it % 5 == 0L) 2 else 1 }),
            Monkey(mutableListOf(93, 91, 69, 64, 72, 89, 50, 71), 0, { it + 1 }, { if (it % 13 == 0L) 5 else 2 }),
            Monkey(mutableListOf(76, 50), 0, { it * it }, { if (it % 7 == 0L) 4 else 6 })
    )

    for (round in (1..10000)) {
        for (m in monkeys) {
            for (item in m.items) {
                val worry = m.inspect(item) % (3*17*2*19*11*5*13*7)
                m.inspections++
                val nextMonkey = m.nextMonkey(worry)
                monkeys[nextMonkey].items.add(worry)
            }
            m.items.clear()
        }
    }

    println(monkeys.sortedByDescending{ it.inspections}.take(2).map{it.inspections}.reduce{ x, y -> x*y} )
}