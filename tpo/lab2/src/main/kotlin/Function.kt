package lab2

import lab2.functions.Log
import lab2.functions.Sec

class Function {
    var sec = Sec()

    var log2 = Log(2.0)
    var log10 = Log(10.0)

    fun func(x: Double, eps: Double): Double {
        return if (x <= 0) {
            ((sec.sec(x) + sec.sec(x)) / sec.sec(x)).let { it * it }
        } else {
            val l10 = log10.log(x, eps) // log10(x)
            val l2 = log2.log(x, eps)   // log2(x)
            val expr = (l10 * l10 * l10 - l10) * l2
            (expr * expr) / l2
        }
    }

}