package net.ulfheim.sellotape

import org.apache.commons.io.FileUtils
import org.apache.log4j.Logger
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait


abstract class SeleniumTest extends GroovyTestCase {
    WebDriver driver
    Logger logger = Logger.getLogger(SeleniumTest)

    void setUp() {
        def driverProperty = System.getProperty('selenium.driver')
        if (driverProperty == 'phantomjs') {
            def caps = new DesiredCapabilities()
            caps.setJavascriptEnabled(true)
            caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true)
            driver = new PhantomJSDriver(caps)

            def handle = driver.getWindowHandle()
            driver.switchTo().window(handle).manage().window().size = new Dimension(1024, 800);
        }
        else if (driverProperty == 'firefox' || driverProperty == null) {
            driver = new FirefoxDriver()
        }
    }

    void takeScreenshot(name) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
        String filename = "screenshot-${name}.png"
        File shotDir = new File(System.getProperty('screenshot.dir') ?: "/tmp/sellotape-screenshots")
        shotDir.mkdirs()
        FileUtils.copyFile(screenshot, new File(shotDir, filename))
    }

    void tearDown() {
        takeScreenshot("Test-${methodName}")
        driver.quit()
    }

    WebElement waitForId(String id, int seconds = 10) {
        logger.debug("Waiting for id $id")
        def by = By.id(id)
        def el = Wait(seconds).until(ExpectedConditions.visibilityOfElementLocated(by))
        return new NeverStaleWebElement(el, driver, by)
    }

    WebElement waitForXpath(String xpath, int seconds = 10) {
        logger.debug("Waiting for xpath $xpath")
        def by = By.xpath(xpath)
        def el = Wait(seconds).until(ExpectedConditions.visibilityOfElementLocated(by))
        return new NeverStaleWebElement(el, driver, by)
    }

    WebElement waitForSelector(String css, int seconds = 10) {
        logger.debug("Waiting for css $css")
        def by = By.cssSelector(css)
        def el = Wait(seconds).until(ExpectedConditions.visibilityOfElementLocated(by))
        return new NeverStaleWebElement(el, driver, by)
    }

    WebElement waitForText(String text, int seconds = 10) {
        logger.debug("Waiting for text $text")
        return waitForXpath("""//*[contains(.,"$text")]""", seconds)
    }

    WebElement hoverOver(WebElement element) {
        def actions = new Actions(driver)
        actions.moveToElement(element).build().perform()
        return element
    }

    void pageShouldNotContain(String text) {
        List list = driver.findElements(By.xpath("""//*[contains(text(), "$text")]"""));
        for (WebElement el : list) {
            assertFalse(el.displayed)
        }
    }

    def Wait(int seconds = 10) {
        new WebDriverWait(driver, seconds)
    }

    static def buildCondition(Closure code) {
        return new ExpectedCondition() {
            @Override Object apply(Object input) {
                return code.call(input)
            }
        }
    }
}
