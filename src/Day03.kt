fun main() {
    fun getMostSignificantBit(numbers: List<String>, bitPosition: Int): Char {
        var count = 0
        numbers.forEach { bits ->
            count += bits[bitPosition].digitToInt()
        }

        return if (count >= (numbers.size / 2.0)) {
            '1'
        } else {
            '0'
        }
    }

    fun getLeastSignificantBit(numbers: List<String>, bitPosition: Int): Char {
        var count = 0
        numbers.forEach { bits ->
            count += bits[bitPosition].digitToInt()
        }

        return if (count < (numbers.size / 2.0)) {
            '1'
        } else {
            '0'
        }
    }

    fun part1(input: List<String>): Int {

        // Assume all entries are the same length
        val length = input.first().length
        var gammaString = ""
        var epsilonString = ""
        for (i in 0 until length) {
            gammaString += getMostSignificantBit(input, i)
            epsilonString += getLeastSignificantBit(input, i)
        }

        return gammaString.toInt(2) * epsilonString.toInt(2)
    }

    fun part2(input: List<String>): Int {

        // Use the gamma bit array to determine oxygen rating
        fun findNumber(
                numbers: List<String>,
                index: Int,
                significantBitFun: (List<String>, Int) -> Char
        ): String {
            val significantBit = significantBitFun(numbers, index)
            val out = numbers.filter { bits ->
                bits[index] == significantBit
            }

            return if (out.size == 1) {
                out.first()
            } else {
                findNumber(out, index.inc(), significantBitFun)
            }
        }

        val oxygen = findNumber(input, 0, ::getMostSignificantBit)
        val c02 = findNumber(input, 0, ::getLeastSignificantBit)
        val oxygenInt = oxygen.toInt(2)
        val c02Int = c02.toInt(2)

        return oxygenInt * c02Int
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))

}
