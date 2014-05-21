package net.ulfheim.sellotape

import org.apache.log4j.Logger
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.internal.Coordinates
import org.openqa.selenium.internal.Locatable


/**
 * Implements proxy around WebElement that captures any "Element is no longer attached to the DOM" errors and
 * finds the new instance of the element
 */
class NeverStaleWebElement implements WebElement, Locatable {

    private Logger logger = Logger.getLogger(NeverStaleWebElement)
    WebElement element
    WebDriver driver
    By foundBy

    NeverStaleWebElement(WebElement element, WebDriver driver, By foundBy) {
        this.element = element
        this.driver = driver
        this.foundBy = foundBy
    }

    @Override
    void click() {
        try {
            element.click()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            element.click()
        }
    }

    @Override
    void submit() {
        try {
            element.submit()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            element.submit()
        }
    }

    @Override
    void sendKeys(CharSequence... keysToSend) {
        try {
            element.sendKeys(keysToSend)
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            element.sendKeys(keysToSend)
        }
    }

    @Override
    void clear() {
        try {
            element.clear()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            element.clear()
        }
    }

    @Override
    String getTagName() {
        try {
            return element.getTagName()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.getTagName()
        }
    }

    @Override
    String getAttribute(String name) {
        try {
            return element.getAttribute(name)
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.getAttribute(name)
        }
    }

    @Override
    boolean isSelected() {
        try {
            return element.isSelected()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.isSelected()
        }
    }

    @Override
    boolean isEnabled() {
        try {
            return element.isEnabled()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.isEnabled()
        }
    }

    @Override
    String getText() {
        try {
            return element.getText()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.getText()
        }
    }

    @Override
    List<WebElement> findElements(By by) {
        try {
            return element.findElements(by)
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.findElements(by)
        }
    }

    @Override
    WebElement findElement(By by) {
        try {
            return element.findElement(by)
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.findElement(by)
        }
    }

    @Override
    boolean isDisplayed() {
        try {
            return element.isDisplayed()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.isDisplayed()
        }
    }

    @Override
    Point getLocation() {
        try {
            return element.getLocation()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.getLocation()
        }
    }

    @Override
    Dimension getSize() {
        try {
            return element.getSize()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.getSize()
        }
    }

    @Override
    String getCssValue(String propertyName) {
        try {
            return element.getCssValue(propertyName)
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return element.getCssValue(propertyName)
        }
    }

    @Override
    Coordinates getCoordinates() {
        try {
            return (element as Locatable).getCoordinates()
        } catch (StaleElementReferenceException ignored) {
            logger.info('Got stale element, attempting to recover')
            element = driver.findElement(foundBy)
            return (element as Locatable).getCoordinates()
        }
    }
}
