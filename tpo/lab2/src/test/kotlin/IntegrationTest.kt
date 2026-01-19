import lab2.Function
import lab2.functions.Cos
import lab2.functions.Ln
import lab2.functions.Log
import lab2.functions.Sec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class FunctionTest {
    private val eps = 1e-6

    @Test
    fun testCosUnit() {
        val cos = Cos()
        assertEquals(1.0, cos.cos(0.0, eps), eps, "cos(0) должен быть равен 1")
        assertEquals(0.0, cos.cos(Math.PI / 2, eps), 1e-5, "cos(π/2) должен быть равен 0")
    }

    @Test
    fun testLnUnit() {
        val ln = Ln()
        assertEquals(0.0, ln.ln(1.0, eps), eps, "ln(1) должен быть равен 0")
        assertEquals(1.0, ln.ln(Math.E, eps), 1e-3, "ln(e) должен быть примерно равен 1")
        assertTrue(ln.ln(-1.0, eps).isNaN(), "ln(-1) должен возвращать NaN")
    }

    @Test
    fun testLogUnit() {
        val log10 = Log(10.0)
        assertEquals(0.0, log10.log(1.0, eps), eps, "log10(1) должен быть равен 0")
        assertEquals(1.0, log10.log(10.0, eps), 1e-3, "log10(10) должен быть равен 1")
        assertTrue(log10.log(-1.0, eps).isNaN(), "log10(-1) должен возвращать NaN")
    }

    @Test
    fun testSecUnit() {
        val sec = Sec()
        assertEquals(1.0, sec.sec(0.0, eps), eps, "sec(0) должен быть равен 1")
    }

    @Test
    fun `test function x branch with mocks`() {
        val log10Mock: Log = mock {
            on { log(eq(2.0), any()) } doReturn 2.0
        }
        val log2Mock: Log = mock {
            on { log(eq(2.0), any()) } doReturn 0.2666667
        }
        val function = Function().apply {
            log10 = log10Mock
            log2 = log2Mock
        }
        val result = function.func(2.0, eps)
        assertEquals(9.6, result, 1e-3, "Для x > 0 функция должна возвращать 9.6 при заданных моках")
    }

    @Test
    fun `test function x 0 branch with mocks`() {
        val secMock: Sec = mock {
            on { sec(eq(-1.0), any()) } doReturn 5.0
        }
        val function = Function().apply {
            sec = secMock
        }
        val result = function.func(-1.0, eps)
        assertEquals(4.0, result, eps, "Для x ≤ 0 функция должна возвращать 4")
    }

    @Test
    fun `test function x equals 1 for logs division by zero with mocks`() {
        val log10Mock: Log = mock {
            on { log(eq(1.0), any()) } doReturn 0.0
        }
        val log2Mock: Log = mock {
            on { log(eq(1.0), any()) } doReturn 0.0
        }
        val function = Function().apply {
            log10 = log10Mock
            log2 = log2Mock
        }
        val result = function.func(1.0, eps)
        assertTrue(result.isNaN(), "Для x = 1 функция должна возвращать NaN из-за деления 0/0")
    }

    @Test
    fun `test sec division by zero with mocks`() {
        val secMock: Sec = mock {
            on { sec(eq(Math.PI / 2), any()) } doReturn Double.NaN
        }
        val result = secMock.sec(Math.PI / 2, eps)
        assertTrue(result.isNaN(), "Mocked sec(π/2) должен возвращать NaN")
    }
}
