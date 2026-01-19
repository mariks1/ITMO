import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.nio.file.Paths
import java.time.Duration
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class CommentTest {
    private lateinit var driver: WebDriver

    @BeforeTest
    fun setUp() {
        val profileDir = Paths.get(
            "C:\\Users\\user\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\guoq2qk6.default-release-1748964699235"
        ).toFile()

        val options = FirefoxOptions().apply {
            addArguments("-profile", profileDir.absolutePath)
            setPageLoadStrategy(PageLoadStrategy.EAGER)
        }
        driver = FirefoxDriver(options)
    }

    @AfterTest
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun likeCommentTest() {
        driver.get("https://mix.com/")
        val wait = WebDriverWait(driver, Duration.ofSeconds(10))
        wait.until(
            ExpectedConditions.urlMatches("""^https://mix\.com/!\d+$""")
        )

    }


    @Test
    fun commentTest() {
        driver.get("https://mix.com/")

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        wait.until(
            ExpectedConditions.urlMatches("""^https://mix\.com/!\d+$""")
        )

        val commentInput: WebElement = driver.findElement(By.xpath(
            "//input[@type=\"text\"]"
        ))

        assertNotNull(commentInput, "Кнопка логина не найдена по XPath")

        commentInput.click()

        commentInput.sendKeys("LOL")
        commentInput.sendKeys(Keys.ENTER)

        Thread.sleep(3000)

        val comments = driver.findElements(
                By.xpath(
                    """//div[
             .//a[@href="/qel"]
             and
             .//div[normalize-space(.)="LOL"]
           ]"""
                )
            )


        assertTrue(comments.isNotEmpty(), "Комментарий от /qel с текстом 'LOL' не найден!")


        Thread.sleep(3000)

    }
}

