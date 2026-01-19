package lab2.functions

import kotlin.math.abs

open class Ln {
    open fun ln(x: Double, eps: Double): Double {
        val y = (x - 1) / (x + 1)
        val y2 = y * y

        return if(x < 0)
            Double.NaN
        else {
            2 * generateSequence(Pair(y, 1)) { (term, n) ->
                val nextTerm = term * y2 * n / (n + 2)
                Pair(nextTerm, n + 2)
            }
                .takeWhile { abs(it.first) > eps }
                .sumOf { it.first }
        }
    }
}