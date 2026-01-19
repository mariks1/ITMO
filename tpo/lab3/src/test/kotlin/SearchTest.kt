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
import kotlin.test.fail


class SearchTest {
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
    fun searchTest() {
        driver.get("https://mix.com/")

        val wait = WebDriverWait(driver, Duration.ofSeconds(10))

        wait.until(
            ExpectedConditions.urlMatches("""^https://mix\.com/!\d+$""")
        )

        val searchButton: WebElement = driver.findElement(
            By.xpath("//a[@href=\"/discover\"]")
        )
        assertNotNull(searchButton, "Кнопка поиска не найдена по XPath")
        searchButton.click()

        Thread.sleep(3000)


        val searchInput: WebElement = driver.findElement(By.xpath(
            "//input[@placeholder=\"Search topics and people\"]"
        ))

        assertNotNull(searchInput, "Поиск не найден по XPath")



        searchInput.click()

        searchInput.sendKeys("Cats")
        searchInput.sendKeys(Keys.ENTER)

        Thread.sleep(3000)

        val follows = driver.findElements(
            By.xpath("//button[normalize-space(.)='Follow']")
        )


        assertTrue(follows.isNotEmpty(), "Комментарий от /qel с текстом 'LOL' не найден!")

        follows[0].click()

        val profilePic: WebElement = driver.findElement(By.xpath(
            "//div[@slot=\"trigger\" and .//a[@href=\"https://mix.com/qel\"]]"
        ))

        profilePic.click()

        val followingBtn: WebElement = driver.findElement(By.xpath(
            "//a[@href=\"/qel/following\"]"
        ))

        followingBtn.click()

        Thread.sleep(3000)

        try {
            driver.findElement(By.xpath(
                "//a[@href=\"/topic/cats\"]"
            ))
        } catch (e: NoSuchElementException) {
            fail("В подписках не появился топик cats!")
        }

    }
}

