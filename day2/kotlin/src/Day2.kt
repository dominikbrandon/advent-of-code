import java.io.File

object Day2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(javaClass.getResource("day2.txt").toURI())
        val regex = Regex("(\\d+)-(\\d+) (\\w): (\\w+)")
        val passwords = file.useLines {
            it.map { line ->
                regex.matchEntire(line)!!
                    .destructured
                    .let { match ->
                        Password(
                            match.component4(),
                            Password.TobogganRentalPolicy(
                                match.component3().first(),
                                match.component1().toInt(),
                                match.component2().toInt()
                            )
                        )
                    }
            }.toList()
        }
        val (validPasswords, invalidPasswords) = passwords.partition { it.isValid() }
        println(validPasswords.count())
    }

    data class Password(
        private val password: String,
        private val policy: Policy
    ) {
        fun isValid(): Boolean = policy.isFulfilledBy(password)

        interface Policy {
            fun isFulfilledBy(phrase: String): Boolean
        }

        data class SledRentalPolicy(
            private val character: Char,
            private val minOccurrences: Int,
            private val maxOccurrences: Int
        ) : Policy {

            override fun isFulfilledBy(phrase: String): Boolean =
                phrase.count { it == character } in minOccurrences..maxOccurrences
        }

        data class TobogganRentalPolicy(
            private val character: Char,
            private val firstPosition: Int,
            private val secondPosition: Int
        ) : Policy {

            override fun isFulfilledBy(phrase: String): Boolean {
                val characters = listOf(phrase[firstPosition - 1], phrase[secondPosition - 1])
                return characters.count { it == character } == 1
            }
        }
    }
}
