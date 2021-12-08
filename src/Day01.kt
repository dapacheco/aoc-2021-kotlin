fun main() {
    fun part1(input: List<String>): Int {
        var increased = 0
        input.map { it.toInt() }.fold(0) { acc, i ->
            if (acc != 0 && i > acc) {
                increased++
            }
            i
        }

        return increased
    }

    fun part2(input: List<String>): Int {
        val windowedDepths = List(input.size) { index ->
            input.drop(index).take(3).sumOf { it.toInt() }
        }
        var increased = 0
        windowedDepths.fold(0) { acc, i ->
            if (acc != 0 && i > acc) {
                increased++
            }
            i
        }
        return increased
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
