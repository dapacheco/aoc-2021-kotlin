fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.single().split(",").map { it.toInt() }

        // for each possible horizontal position work out the cost to align
        val sums = numbers.map { num ->
            numbers.sumOf { checkNum ->
                maxOf(num, checkNum) - minOf(num, checkNum)
            }
        }

        return sums.minOf { it }
    }

    fun part2(input: List<String>): Int {
        val numbers = input.single().split(",").map { it.toInt() }

        // find the number range
        val max = numbers.maxOf { it }
        val min = numbers.minOf { it }
        
        val sums = (min .. max).map { num ->
            numbers.sumOf { checkNum ->
                val diff = maxOf(num, checkNum) - minOf(num, checkNum)
                (diff * (diff + 1)) / 2 // calculate the sum of the numbers from 1 to diff
            }
        }

        return sums.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
