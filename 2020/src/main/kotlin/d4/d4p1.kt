package d4

fun toPassport(lines: List<String>): Passport {
    return Passport(lines.map{ line ->
        val split = line.split(":")
        Pair(split[0], split[1])
    })
}

data class Passport(val tags: List<Pair<String, String>>)
val yearRegex = Regex("^[0-9]+$")
fun numericBetween(str: String, min: Int, max: Int): Boolean {
    if (yearRegex.matches(str)) {
        val year = str.toInt()
        return year >= min && year <= max
    } else {
        return false
    }
}

fun validBirthYear(str: String): Boolean {
    return numericBetween(str, 1920, 2002)
}

fun validIssueYear(str: String): Boolean {
    return numericBetween(str, 2010, 2020)
}
fun validExpirationYear(str: String): Boolean {
    return numericBetween(str, 2020, 2030)
}
fun validHeight(str: String): Boolean {
    if (str.endsWith("in")) {
        return numericBetween(str.substring(0, str.length - 2), 59, 76)
    } else if (str.endsWith("cm")) {
        return numericBetween(str.substring(0, str.length - 2), 150, 193)
    } else {
        return false
    }
}
fun validHairColor(str: String): Boolean {
    return Regex("^#[0-9a-f]{6}$").matches(str)
}
fun validEyeColor(str: String): Boolean {
    return setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(str)
}
fun validPassportId(str: String): Boolean {
    return Regex("^[0-9]{9}$").matches(str)
}

val validations = mapOf(
    Pair("byr", ::validBirthYear),
    Pair("iyr", ::validIssueYear),
    Pair("eyr", ::validExpirationYear),
    Pair("hgt", ::validHeight),
    Pair("hcl", ::validHairColor),
    Pair("ecl", ::validEyeColor),
    Pair("pid", ::validPassportId)
)

fun isValid(passport: Passport): Boolean {
    val tagKeys = passport.tags.map{it.first}.toSet()
    return tagKeys.containsAll(validations.keys)
}

fun isMoreValid(passport: Passport): Boolean {
    if (!isValid(passport)) {
        return false
    }

    return passport.tags.filter { validations.containsKey(it.first) }
        .all{ validations.getValue(it.first).invoke(it.second) }
}

fun main() {
    val content = Class::class.java.getResource("/d4/d4p1").readText()
    val list = content.split("\n")

    val (_, passports) = list.fold(
        Pair(listOf(), listOf()),
        { passports: Pair<List<String>, List<List<String>>>, line: String ->
            if (line.isBlank()) {
                Pair(listOf(), passports.second.plusElement(passports.first))
            } else {
                val split = line.split(" ")
                Pair(passports.first + split, passports.second)
            }
        }
    )

    println(passports.map(::toPassport).filter(::isValid).count())
    println(passports.map(::toPassport).filter(::isMoreValid).count())
}
