package d20

import utils.*

data class Rx(val name: String, var state: Boolean, val targets: List<String>, val queue: MutableList<Triple<String, String, Boolean>>): Module {
    override fun handlePulse(from: String, pulse: Boolean) {
        if (!pulse) {
            state = true
            targets.forEach{ queue.add(Triple(name, it, state)) }
        }
    }

    override fun name(): String {
        return name
    }

    override fun targets(): List<String> {
        return targets
    }

    override fun addInput(from: String) {

    }

}

fun main() {
    val queue = mutableListOf<Triple<String, String, Boolean>>()
    val lines = readList("/d20.txt").map {
        val targets = it.split(">")[1].split(",").map{ it.trim()}.filter{ it != "" }
        if (it.startsWith("broadcaster")) {
            Broadcaster("broadcaster", targets,  queue)
        } else if (it.startsWith("%")) {
            val name = it.split("-")[0].trim().substring(1)
            Flipflop(name, false, targets, queue)
        } else {
            val name = it.split("-")[0].trim().substring(1)
            Conjunction(name, mutableMapOf(), targets, queue)
        }
    }.toMutableList()
    val button = Button("button", "broadcaster", queue)
    lines.add(button)
    val rx = Rx("rx", false, listOf(), queue)
    lines.add(rx)

    val mods = lines.associateBy{ it.name() }

    for (mod in lines) {
        val targets = mod.targets()
        targets.forEach {
            if (it in mods.keys) {
                mods[it]!!.addInput(mod.name())
            }
        }
    }


    var queueIdx = 0
    var presses = 0
    while(!rx.state) {
        queueIdx = 0
        queue.clear()

        button.push()
        presses += 1
        while(queueIdx < queue.size) {
            val pulse = queue[queueIdx]

            if (pulse.second == "bn" && pulse.third) {
                /*
                bn feeds into rx. Used this print statement to manually determine the 4 modules feeding into bn all
                have a fixed number of cycles between outputting true.  Did LCM on wolfram alpha for the 4 cycles
                to determine the cycle on which all 4 modules feeding bn will emit true (and thus bn will emit false to rx)
                lz: 4003,
                zm: 3823
                mz: 3881
                pl: 3797
                 */

                println("PressNumber $presses, from ${pulse.first}, value: ${pulse.third}")
            }

            if (pulse.second in mods.keys) {
                mods[pulse.second]!!.handlePulse(pulse.first, pulse.third)
            }
            queueIdx += 1
        }
        if (presses % 1000000 == 0) println(presses)
    }

    println("Success: $presses")
}