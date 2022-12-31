package burujiyaseer.example.wordle

import java.util.*
import kotlin.random.Random

class Words{
    private val wordsArray: List<String> = listOf("asset","broom","Chair","array","Guess", "Brain","Brand","Chain","Drain","laugh","smile","happy")

    fun getTodayWord() : String {
        val until = wordsArray.size
        val seed = getTodaySeed()
        val todayIdx = Random(seed).nextInt(from = 0, until)
        return wordsArray[todayIdx]
    }
    /**
     * the function getTodaySeed() returns a unique integer number everyday, which in turn is passed
     * into the getTodayWord() function, and it is then randomized to provide a unique word everyday.
     */

    private fun getTodaySeed(): Int {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return year + month + day
    }
}