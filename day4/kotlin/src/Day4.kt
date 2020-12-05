import java.io.File

object Day4 {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(javaClass.getResource("day4.txt").toURI())
        val validPassports = file.readPassports().filter { it.isValid() }.count()
        println(validPassports)
    }

    private fun File.readPassports(): Sequence<Passport> {
        return useLines { lines ->
            val iterator = lines.toList().iterator()
            generateSequence { iterator.getNextPassport() }
        }
    }

    private fun Iterator<String>.getNextPassport(): Passport? {
        if (!hasNext()) {
            return null
        }
        val buffer = mutableListOf<String>()
        while (hasNext() && buffer.lastOrNull()?.isBlank() != true) {
            buffer.add(next())
        }
        val rawString = buffer.joinToString(" ")
        return Passport.from(rawString)
    }

    data class Passport(
        private val entries: Map<String, String>
    ) {
        companion object {
            private val HEIGHT_REGEX = Regex("(\\d+)(\\w+)")
            private val HAIR_COLOR_REGEX = Regex("#[0-9a-f]{6}")
            private val EYE_COLORS = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            private val PASSWORD_ID_REGEX = Regex("[0-9]{9}")

            fun from(rawString: String): Passport {
                val rawEntries = rawString.trim().split(" ")
                val entries = rawEntries.map { it.split(":") }.associate { Pair(it.first(), it.last()) }
                return Passport(entries)
            }
        }

        fun isValid() = isBirthYearValid()
                && isIssueYearValid()
                && isExpirationYearValid()
                && isHeightValid()
                && isHairColorValid()
                && isEyeColorValid()
                && isPasswordIdValid()

        private fun isBirthYearValid() = entries["byr"]?.toInt() in 1920..2002

        private fun isIssueYearValid() = entries["iyr"]?.toInt() in 2010..2020

        private fun isExpirationYearValid() = entries["eyr"]?.toInt() in 2020..2030

        private fun isHeightValid() = entries["hgt"]?.let { rawHeight ->
            HEIGHT_REGEX.matchEntire(rawHeight)!!.destructured.let { match ->
                when (match.component2()) {
                    "cm" -> match.component1().toInt() in 150..193
                    "in" -> match.component1().toInt() in 59..76
                    else -> false
                }
            }
        } ?: false

        private fun isHairColorValid() = entries["hcl"]?.let { HAIR_COLOR_REGEX.matches(it) } ?: false

        private fun isEyeColorValid() = entries["ecl"] in EYE_COLORS

        private fun isPasswordIdValid() = entries["pid"]?.let { PASSWORD_ID_REGEX.matches(it) } ?: false
    }
}
