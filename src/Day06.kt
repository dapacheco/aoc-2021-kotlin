
fun main() {

    fun numberOffishAfterDays(days: Int, startingFish: List<String>): Long {
        val fish = startingFish.single().split(",").map { it.toInt() }

        // 0 - 8
        val fishTimers = LongArray(9)
        // Set the initial state based on the input
        fish.forEach { fishTimers[it]++ }

        repeat(days) {
            val newFish = fishTimers[0] // Number of fish that will spawn
            (1 .. 8).forEach {
                fishTimers[it - 1] = fishTimers[it] // move all down
            }
            fishTimers[6] += newFish
            fishTimers[8] = newFish
        }

        // Count the total fish
        return fishTimers.sum()
    }

    fun part1(input: List<String>) = numberOffishAfterDays(80, input)
    fun part2(input: List<String>) = numberOffishAfterDays(256, input)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
