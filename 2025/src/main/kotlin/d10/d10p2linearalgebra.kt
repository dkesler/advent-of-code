package d10

import org.apache.commons.math3.optim.linear.*
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType
import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar
import utils.readList

fun main() {
    val machines = readList("/d10.txt").map{ toJoltageMachine(it)}
    val presses = machines.map{ machine ->
        minPressesLA(machine)
    }
    println(presses.sum())
}

fun minPressesLA(machine: JoltageMachine): Int {
    val model = Model()
    val maxJoltage = machine.goal.maxOrNull()!!
    val vars = machine.buttons.map{ model.intVar(0, maxJoltage) }
    val tot = model.intVar(0, maxJoltage * vars.size )

    machine.goal.indices.map { jIdx ->
        val affecting = machine.buttons.indices.filter{ machine.buttons[it].contains(jIdx) }
        model.sum(
            affecting.map{vars[it]}.toTypedArray(),
            "=",
            machine.goal[jIdx]
        ).post()
    }
    model.sum(vars.toTypedArray(), "=", tot).post()
    val res = model.solver.findOptimalSolution(tot, Model.MINIMIZE)
    val buttons = vars.map{ res.getIntVal(it)}
    println("${res.getIntVal(tot)} = $buttons")
    return res.getIntVal(tot)
}