package d10

import utils.Grids
import utils.readList

data class JoltageMachine(val goal: List<Int>, val buttons: List<List<Int>>) {
    fun withoutButtonHead(): JoltageMachine {
        val newButtons = buttons.subList(1, buttons.size)

        //val target = goal.indices.minByOrNull { joltage -> buttons.count { it.contains(joltage) } }!!
        //val sortedButtons = newButtons.sortedBy { -it.size }.sortedBy{ if (it.contains(target)) 0 else 1 }

        return JoltageMachine(goal, newButtons)
    }
}

fun main() {
    val machines = readList("/d10.txt").map{ toJoltageMachine(it)}

    println(machines.sumBy{ minPresses(it) })

}

fun minPressesAStar(joltageMachine: JoltageMachine): Int {
    data class State(val joltageMachine: JoltageMachine, val joltages: List<Int>)
    val min = Grids.aStar<State>(
        setOf(State(joltageMachine, joltageMachine.goal.map{0})),
        { state:State -> state.joltages == joltageMachine.goal },
        { state: State ->
            val next = mutableSetOf<State>()

            if (state.joltageMachine.buttons.isNotEmpty()) {
                next.add(State(state.joltageMachine.withoutButtonHead(), state.joltages))
                val doPress = State(state.joltageMachine, applyJoltages(state.joltages, state.joltageMachine.buttons.first(), 1))
                if (isValidState(doPress.joltageMachine, doPress.joltages) && !unreachable(doPress.joltageMachine, doPress.joltages)) {
                    next.add(doPress)
                }
            }

            next
        },
        { cur: State, next: State ->
           if (cur.joltageMachine.buttons.size == next.joltageMachine.buttons.size) 1 else 0
        },
        { state: State ->
            state.joltageMachine.goal.zip(state.joltages).maxOf{ (a, b) -> a - b }.toLong()
        }
    )
    println(min.cost)
    return min.cost.toInt()
}

fun applyJoltages(state: List<Int>, button: List<Int>, times: Int): List<Int> {
    val newState = state.toMutableList()
    for (battery in button) {
        newState[battery] += times
    }
    return newState
}

fun isValidState(joltageMachine: JoltageMachine, stateIfButtonsPressed: List<Int>): Boolean {
    return joltageMachine.goal.zip(stateIfButtonsPressed).all{ (goal, cur) -> cur <= goal }
}

fun unreachable(joltageMachine: JoltageMachine, state: List<Int>): Boolean {
    for (i in state.indices) {
        val cur = state[i]
        val goal = joltageMachine.goal[i]
        if (cur < goal) {
            if (joltageMachine.buttons.none { it.contains(i) }) return true
        }
    }
    return false
}

fun findLastChance(joltageMachine: JoltageMachine, state: List<Int>): Int? {
    for (i in state.indices) {
        if (state[i] < joltageMachine.goal[i]) {
            if (joltageMachine.buttons.first().contains(i)
                && joltageMachine.buttons.subList(1, joltageMachine.buttons.size).none{ it.contains(i) }) return i
        }
    }
    return null
}

fun findPressesToDo(machine: JoltageMachine, state: List<Int>, i: Int): Int {
    return machine.goal[i] - state[i]
}

fun addCapping(a: Int, b: Int): Int {
    if (a == Int.MAX_VALUE || b == Int.MAX_VALUE) return Int.MAX_VALUE else return a + b
}

var ct = 0
val start = System.currentTimeMillis()
fun minPresses(joltageMachine: JoltageMachine): Int {
    fun minPressesLoop(joltageMachine: JoltageMachine, state: List<Int>): Int {
        fun tryPress(button: List<Int>, numPresses: Int): Int {
            val stateIfButtonsPressed = applyJoltages(state, button, numPresses)
            return if (isValidState(joltageMachine, stateIfButtonsPressed)) {
                addCapping(minPressesLoop(joltageMachine.withoutButtonHead(), stateIfButtonsPressed), numPresses)
            } else {
                Int.MAX_VALUE
            }
        }

        fun rangeCheck2(min: Int, max: Int, button: List<Int>): Int {
            val memoTable = mutableMapOf<Int, Int>()
            fun tryPress(numPresses: Int): Int {
                if (numPresses in memoTable) memoTable[numPresses]!!
                val stateIfButtonsPressed = applyJoltages(state, button, numPresses)
                val res = if (isValidState(joltageMachine, stateIfButtonsPressed)) {
                    addCapping(minPressesLoop(joltageMachine.withoutButtonHead(), stateIfButtonsPressed), numPresses)
                } else {
                    Int.MAX_VALUE
                }
                memoTable[numPresses] = res
                return res
            }
            val rangesToCheck = mutableListOf(min..max)
            var bestFound = Int.MAX_VALUE
            while(rangesToCheck.isNotEmpty()) {
                val toCheck = rangesToCheck.first()
                rangesToCheck.removeAt(0)
                val minPresses = tryPress(toCheck.start)
                val maxPresses = tryPress(toCheck.last)
                val median = (toCheck.start + toCheck.last) / 2
                if (minPresses > bestFound && maxPresses > bestFound) continue
                val medPresses = tryPress(median)

                if (medPresses < bestFound) bestFound = medPresses
                if (minPresses < bestFound) bestFound = minPresses
                if (maxPresses < bestFound) bestFound = maxPresses

                if (toCheck.last - toCheck.start > 2) {
                    val medM1 = if (median-1 in toCheck) median-1 else toCheck.first
                    val medP1 = if (median+1 in toCheck) median+1 else toCheck.last
                    val medP1Presses = tryPress(medP1)
                    val medM1Presses = tryPress(medM1)

                    if (medP1Presses < bestFound) bestFound = medP1Presses
                    if (medM1Presses < bestFound) bestFound = medM1Presses
                    if (medP1Presses == Int.MAX_VALUE && medM1Presses == Int.MAX_VALUE && medPresses < Int.MAX_VALUE) return medPresses
                    if (medP1Presses > medPresses && medM1Presses > medPresses) return medPresses

                    rangesToCheck.add(toCheck.start..medM1)
                    rangesToCheck.add(medP1..toCheck.last)
                }
            }

            return bestFound
        }

        fun rangeCheck(min: Int, max: Int, button: List<Int>): Int {
            if (max-min <= 2) {
                val minOrNull = (min..max).map {
                    tryPress(button, it)
                }.minOrNull()
                return minOrNull!!
            }

            val median = (max + min) / 2

            val lo = tryPress(button, median-1)
            val med = tryPress(button, median)
            val hi = tryPress(button, median+1)

            if (med == Int.MAX_VALUE) {
                if (hi == Int.MAX_VALUE && lo == Int.MAX_VALUE) {
                    //return minOf( rangeCheck(min, median-1, button), rangeCheck(median+1, max, button) )
                    return rangeCheck(min, median-1, button)
                } else if (hi == Int.MAX_VALUE) {
                    return rangeCheck(min, median-1, button)
                } else {
                    return rangeCheck(median+1, max, button)
                }
            } else {
                if (hi == Int.MAX_VALUE && lo == Int.MAX_VALUE) {
                    return med
                } else {
                    if (med <= lo && med <= hi) return med
                    if (lo < med) return rangeCheck(min, median-1, button)
                    return rangeCheck(median+1, max, button)
                }
            }
/*
            if (med <= lo && med <= hi) return med
            if (lo < med) return rangeCheck(min, median-1, button)
            //println("${min}, ${median+1}, ${max}")

            return rangeCheck(median+1, max, button)
*/
        }

        if (state == joltageMachine.goal) {
            return 0
        }
        if (joltageMachine.buttons.isEmpty()) return Int.MAX_VALUE
        if (unreachable(joltageMachine, state)) return Int.MAX_VALUE

        val lastChancer = findLastChance(joltageMachine, state)

        if (lastChancer != null) {
            val pressesToDo = findPressesToDo(joltageMachine, state, lastChancer)
            val stateIfButtonsPressed = applyJoltages(state, joltageMachine.buttons.first(), pressesToDo)
            if (isValidState(joltageMachine, stateIfButtonsPressed))
                return addCapping(minPressesLoop( joltageMachine.withoutButtonHead(), stateIfButtonsPressed), pressesToDo)
            else
                return Int.MAX_VALUE
        } else {
            val buttonToTry = joltageMachine.buttons.first()
            val maxPresses = buttonToTry.minOf{ findPressesToDo(joltageMachine, state, it)}
            //return rangeCheck(0, maxPresses, buttonToTry)
            var best = Int.MAX_VALUE
            for (presses in (maxPresses downTo  0)) {
                val stateIfButtonsPressed = applyJoltages(state, buttonToTry, presses)
                if (isValidState(joltageMachine, stateIfButtonsPressed)) {
                    val mp = addCapping(minPressesLoop(joltageMachine.withoutButtonHead(), stateIfButtonsPressed), presses)
                    if (mp < best) best = mp
                    if (mp > best) return best
                }
            }
            return best
        }

        /*else if (isValidState(joltageMachine, stateIfButtonsPressed)) {
            val ifButtonPressedRaw = minPressesLoop(joltageMachine, applyJoltages(state, joltageMachine.buttons.first(), pressesToDo))
            val ifButtonPressed = if (ifButtonPressedRaw == Int.MAX_VALUE) Int.MAX_VALUE else ifButtonPressedRaw + pressesToDo
            val ifButtonNotPressed = minPressesLoop(joltageMachine.withoutButtonHead(), state)
            val res = min(ifButtonPressed, ifButtonNotPressed)
            return res
        } else {
            val ifButtonNotPressed = minPressesLoop(joltageMachine.withoutButtonHead(), state)
            return ifButtonNotPressed
        }*/

    }
    val lineStart = System.currentTimeMillis()
    val minPressesLoop = minPressesLoop(joltageMachine, joltageMachine.goal.map { 0 })
    val end = System.currentTimeMillis()
    println("Completed line $ct in ${end - lineStart}ms, took $minPressesLoop presses.  Cumulative time elapsed: ${ end - start }")
    ct++
    return minPressesLoop
}

fun apply(state: Set<Int>, button: List<Int>): Set<Int> {
    val newState = state.toMutableSet()
    for (light in button) {
        if (light in newState) newState.remove(light) else newState.add(light)
    }
    return newState
}

fun toJoltageMachine(it: String): JoltageMachine {
    val s = it.split(" ")
    val buttons = s.subList(1, s.size-1).map{
        val lights = it.replace("(", "").replace(")", "").split(",")
        lights.map{it.toInt()}
    }.sortedBy { it.size }.reversed()

    val joltages = s[s.size-1].substring(1, s[s.size-1].length - 1).split(",").map{it.toInt()}

    return JoltageMachine(joltages, buttons)
}
