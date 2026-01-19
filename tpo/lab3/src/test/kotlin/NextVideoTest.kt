import org.openqa.selenium.By
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


class NextVideoTest {
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
    fun clickNextButton() {
        driver.get("https://mix.com/")

        val wait = WebDriverWait(driver, Duration.ofSeconds(3))

        wait.until(
            ExpectedConditions.urlMatches("""^https://mix\.com/!\d+$""")
        )

        var nextBtn: WebElement = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space(.)='Next']]")
            )
        )

        assertNotNull(nextBtn, "Кнопка логина не найдена по XPath")

        val oldLink = driver.currentUrl

        nextBtn.click()
        val newLink = driver.currentUrl

        assertTrue(
            newLink != oldLink,
            "$newLink, $oldLink!"
        )

        nextBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space(.)='Next']]")
            )
        )

        nextBtn.click()
        val newLink2 = driver.currentUrl

        assertTrue(
            newLink2 != newLink,
            "$newLink, $newLink2!"
        )

    }
}

