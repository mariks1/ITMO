package lab2

import lab2.functions.Cos
import lab2.functions.Ln
import lab2.functions.Log
import lab2.functions.Sec
import java.io.File
import java.io.FileWriter


fun writeToCSV(x: List<Double>, eps: Double, filename: String, func: (Double, Double) -> Double) {
    val file = File(filename)
    FileWriter(file).use { writer ->
        if (file.length() == 0L) {
            writer.append("X,Результат модуля (X)\n")
        }
        x.forEach { writer.append("$it,${func(it, eps)}\n") }
    }
}


fun main() {
    val cos = Cos()
    val sec = Sec()

    val ln = Ln()
    val log = Log(3.0)
    val func = Function()

    println("cos: ${cos.cos(10.0, 0.0000001)}")
    println("sec: ${sec.sec(10.0, 0.0000001)}")
    println("ln: ${ln.ln(10.0, 0.0000001)}")
    println("log3: ${log.log(10.0, 0.0000001)}")
    println("func 10: ${func.func(1.1, 0.0000001)}")
    println("func -10: ${func.func(0.0, 0.0000001)}")

    val funcTestValues: List<Double> = listOf(-5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0)
    writeToCSV(funcTestValues, 0.00001, "src/main/resources/FuncTest.csv", func::func)
    writeToCSV(funcTestValues, 0.00001, "src/main/resources/TanTest.csv", cos::cos)

}