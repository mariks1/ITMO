import org.openqa.selenium.By
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.nio.file.Paths
import java.time.Duration
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.fail


class SaveVideoTest {
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
    fun saveVideoTest() {
        driver.get("https://mix.com/")

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        wait.until(
            ExpectedConditions.urlMatches("""^https://mix\.com/!\d+$""")
        )

        val id = driver.currentUrl?.substringAfter("!")

        Thread.sleep(3000)

        val shareBtn: WebElement = driver.findElements(
            By.xpath(".//button[.//*[local-name()='svg' and contains(@class,'size-10')]]")
        )[1]

        assertNotNull(shareBtn, "Кнопка поделиться не найдена по XPath")
        shareBtn.click()

        Thread.sleep(3000)

        val saveBtn: WebElement = driver.findElement(By.xpath(
            "//button[.//div[normalize-space(.)='Save']]"
        ))

        assertNotNull(saveBtn, "Кнопка сохранить не найдена по XPath")

        saveBtn.click()

        Actions(driver)
            .moveByOffset(10, 10)
            .click()
            .perform()

        Thread.sleep(3000)

        val profilePic: WebElement = driver.findElement(By.xpath(
            "//div[@slot=\"trigger\" and .//a[@href=\"https://mix.com/qel\"]]"
        ))

        profilePic.click()

        Thread.sleep(3000)

        val savedBtn: WebElement = driver.findElement(By.xpath(
            "//a[@href=\"/qel/saved\"]"
        ))

        savedBtn.click()

        Thread.sleep(3000)

        try {
            driver.findElement(By.xpath(
                "//div[@data-key=\"$id\"]"
            ))
        } catch (_: NoSuchElementException) {
            fail("В сохраненных не появилось видел!")
        }

    }
}

