package d20

import utils.*

interface Module {
    fun handlePulse(from: String, pulse: Boolean)
    fun name(): String
    fun targets(): List<String>
    fun addInput(from: String)
}

data class Button(val name: String, val target: String, val queue: MutableList<Triple<String, String, Boolean>>): Module  {
    override fun handlePulse(from: String, pulse: Boolean) {}
    override fun name(): String {
        return name
    }

    override fun targets(): List<String> {
        return listOf(target)
    }

    override fun addInput(from: String) {

    }

    fun push() {
        queue.add(Triple(name, target, false))
    }
}
data class Broadcaster(val name: String, val targets: List<String>, val queue: MutableList<Triple<String, String, Boolean>>): Module {
    override fun handlePulse(from: String, pulse: Boolean) {
        targets.forEach{queue.add(Triple(name, it, pulse)) }
    }

    override fun name(): String { return name }
    override fun targets(): List<String> {
        return targets
    }

    override fun addInput(from: String) {
    }
}
data class Flipflop(val name: String, var state: Boolean, val targets: List<String>, val queue: MutableList<Triple<String, String, Boolean>>): Module {
    override fun handlePulse(from: String, pulse: Boolean) {
        if (pulse == false) {
            state = !state
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
data class Conjunction(val name: String, val state: MutableMap<String, Boolean>, val targets: List<String>, val queue: MutableList<Triple<String, String, Boolean>>): Module {
    override fun handlePulse(from: String, pulse: Boolean) {
        state[from] = pulse
        if (state.values.all{it}) {
            targets.forEach{ queue.add(Triple(name, it, false))}
        } else {
            targets.forEach{ queue.add(Triple(name, it, true))}
        }
    }

    override fun addInput(from: String){
        state[from] = false
    }


    override fun name(): String {
        return name
    }

    override fun targets(): List<String> {
        return targets
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
    val rx = Flipflop("rx", false, listOf(), queue)
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
    for (b in (1..1000)) {
        button.push()
        while(queueIdx < queue.size) {
            val pulse = queue[queueIdx]
            if (pulse.second in mods.keys) {
                mods[pulse.second]!!.handlePulse(pulse.first, pulse.third)
            }
            queueIdx += 1
        }
    }

    println (
            queue.filter{ it.third }.count() * queue.filter{ !it.third}.count()
    )
}