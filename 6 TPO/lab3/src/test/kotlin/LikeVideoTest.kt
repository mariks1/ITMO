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
import kotlin.test.assertTrue
import kotlin.test.fail


class LikeVideoTest {
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
    fun likeVideoThenCheckProfile() {
        driver.get("https://mix.com/")

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        wait.until(
            ExpectedConditions.urlMatches("""^https://mix\.com/!\d+$""")
        )

        val likeBtn: WebElement = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("""//button[
                    .//*[local-name() = 'svg' and not(contains(@class,'rotate-180'))]
                and
                .//*[local-name() = 'path' and contains(@class,'stroke-primary')]
              ]""")
            )
        )

        assertNotNull(likeBtn, "Кнопка логина не найдена по XPath")

        val id = driver.currentUrl?.substringAfter("!")

        likeBtn.click()

        val profilePic: WebElement = driver.findElement(By.xpath(
            "//div[@slot=\"trigger\" and .//a[@href=\"https://mix.com/qel\"]]"
        ))

        Actions(driver)
            .moveToElement(profilePic)
            .pause(Duration.ofMillis(500))
            .perform()

        val profileBtn: WebElement = driver.findElement(By.xpath(
            "//div[@slot=\"trigger\" and .//a[@href=\"/qel\"]]"
        ))

        profileBtn.click()

        Thread.sleep(3000)

        try {
            driver.findElement(By.xpath(
                "//div[@data-key=\"$id\"]"
            ))
        } catch (e: NoSuchElementException) {
            fail("Элемент с data-key='$id' не найден!")
        }


        Thread.sleep(3000)

    }
}

