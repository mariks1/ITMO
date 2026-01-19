package lab2.functions


open class Log(private val n: Double) {
    private val ln: Ln = Ln()

    open fun log(x: Double, eps: Double): Double {
        return if(x < 0){
            Double.NaN
        }
        else{
            ln.ln(x, eps) / ln.ln(n, eps)
        }
    }
}
