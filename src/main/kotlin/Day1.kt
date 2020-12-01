import java.io.File

object Day1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File(javaClass.getResource("day1.txt").toURI())
        val numbers = file.useLines { seq -> seq.map { it.toInt() }.toList() }
        val multiplication = numbers.let {
            it.forEach { a ->
                it.forEach { b ->
                    it.forEach { c ->
                        if (a + b + c == 2020) return@let a * b * c
                    }
                }
            }
        }
        println(multiplication)
    }
}
