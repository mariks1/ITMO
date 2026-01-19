package lab2.functions

import kotlin.math.abs
import kotlin.math.PI


open class Cos {
    open fun cos(x: Double, eps: Double = 1e-6): Double {

        val xNorm = x % (2 * PI)
        var result = 0.0
        var term = 1.0
        var n = 0

        result += term
        n++

        while (true) {
            term *= -xNorm * xNorm / ((2 * n - 1) * (2 * n))
            result += term

            if (abs(term) < eps) break

            n++
        }

        return result
    }
}


