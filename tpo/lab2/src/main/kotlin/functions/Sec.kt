package lab2.functions

open class Sec {
    var cos = Cos()

    open fun sec(x: Double, eps: Double = 1e-6): Double {
        return (1 / cos.cos(x, eps))
    }
}