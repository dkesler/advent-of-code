package d10

import utils.readList
import kotlin.math.min

data class LightMachine(val goal: Set<Int>, val buttons: List<List<Int>>) {
    fun withoutButtonHead(): LightMachine {
        return LightMachine(goal, buttons.subList(1, buttons.size))
    }
}

fun main() {
    val machines = readList("/d10.txt").map{ toLightMachine(it)}

    println(machines.sumBy{ minPresses(it) })

}



fun minPresses(machine: LightMachine): Int {

    fun minPressesLoop(machine: LightMachine, state: Set<Int>): Int {
        if (state == machine.goal) return 0
        if (machine.buttons.isEmpty()) return Int.MAX_VALUE

        val ifButtonPressedRaw = minPressesLoop(machine.withoutButtonHead(), apply(state, machine.buttons.first()))
        val ifButtonPressed = if (ifButtonPressedRaw == Int.MAX_VALUE) Int.MAX_VALUE else ifButtonPressedRaw + 1
        val ifButtonNotPressed = minPressesLoop(machine.withoutButtonHead(), state)
        return min(ifButtonPressed, ifButtonNotPressed)
    }
    return minPressesLoop(machine, setOf())
}


fun toLightMachine(it: String): LightMachine {
    val s = it.split(" ")
    val buttons = s.subList(1, s.size-1).map{
        val lights = it.replace("(", "").replace(")", "").split(",")
        lights.map{it.toInt()}
    }

    val goals = mutableSetOf<Int>()
    val lights = s[0].substring(1, s[0].length - 1)
    for (charIdx in lights.indices) {
        if (lights[charIdx] == '#') goals.add(charIdx)
    }

    return LightMachine(goals, buttons)
}
