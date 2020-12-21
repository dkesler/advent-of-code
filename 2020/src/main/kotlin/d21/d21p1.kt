package d21

fun toFood(line: String): Food {
    val (ing, allergens) = line.split("(contains")
    return Food(
        ing.split(" ").map{it.trim()}.filter{ it != ""},
        allergens.split(",").map{it.replace(')', ' ').trim()}
    )
}
data class Food(val ingredients: List<String>, val allergens: List<String>)

fun getPossibleIng(allergen: String, food: List<Food>): Set<String> {
    val foodWithAllergen = food.filter { it.allergens.contains(allergen) }
    val ingredientSetsWithAllergen = foodWithAllergen.map { it.ingredients.toSet() }

    return ingredientSetsWithAllergen
        .reduce{a, b -> a.intersect(b)}
}

fun main() {
    val content = Class::class.java.getResource("/d21/d21p1").readText()
    val list = content.split("\n").filter{it != ""}.map(::toFood)

    val allAllergens = list.flatMap{it.allergens}.toSet()

    val allergenMap = allAllergens.map{
        Pair(it, getPossibleIng(it, list))
    }.toMap()

    val allAllergenContainingIng = allergenMap.values.flatMap { it }.toSet()

    val ingWithoutAllergies = list.flatMap{it.ingredients}.toSet().filter { !allAllergenContainingIng.contains(it) }

    println(ingWithoutAllergies.map{ ing -> list.filter{it.ingredients.contains(ing)}.count()}.sum())

    var m = allergenMap.mapValues{ it.value.filter{ !ingWithoutAllergies.contains(it)}}
    val allergyToIngMap = mutableMapOf<String, String>()
    val unallocatedIng = m.values.flatMap { it }.toMutableSet()

    while(unallocatedIng.isNotEmpty()) {
        val nextAllergy = m.filter{ it.value.size == 1 }.keys.first()
        val nextIng = m.getValue(nextAllergy).first()

        allergyToIngMap.put(nextAllergy, nextIng)

        unallocatedIng.remove(nextIng)

        m = m.mapValues { it.value.filter{ it != nextIng } }
    }

    println(allergyToIngMap)

    println(allergyToIngMap.entries.sortedBy { it.key }.map{it.value}.joinToString(","))
}
