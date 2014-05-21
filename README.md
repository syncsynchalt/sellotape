# Sellotape

Thin wrapper for selenium which automatically resolves timing errors and "Element not found in DOM" exceptions.

## To compile

Build the library using `./gradlew`.  Jar is output in `build/libs`.
Let me know if you're interested in having this packaged up for
maven, gradle, etc.

## To use

Extend the class SeleniumTest, which automatically starts a webdriver available as `driver`.  Add your tests, which must start with the prefix 'test'.  The following methods are available in SeleniumTest:

- `void takeScreenshot(name)`
    Takes extra screenshot named screenshot-{name}.png

- `WebElement waitForId(String id, int seconds = 10)`
    Waits {10 seconds} for visible element with html id of {id}.

    Returns an element which can be acted on with `click()`, `clear()`, `sendKeys()`, or any other WebElement method.

- `WebElement waitForXpath(String xpath, int seconds = 10)`
    Waits {10 seconds} for visible element with xpath of {xpath}.

    Returns an element which can be acted on with `click()`, `clear()`, `sendKeys()`, or any other WebElement method.

- `WebElement waitForSelector(String css, int seconds = 10)`
    Waits {10 seconds} for visible element with CSS selector of {css}.

    Returns an element which can be acted on with `click()`, `clear()`, `sendKeys()`, or any other WebElement method.

- `WebElement waitForText(String text, int seconds = 10)`
    Waits {10 seconds} for visible element containing text {text}.

    Returns an element which can be acted on with `click()`, `clear()`, `sendKeys()`, or any other WebElement method.

- `WebElement hoverOver(WebElement element)`
    Hover over the given element.

    Returns the input element for chaining actions such as `click()`.

- `def pageShouldNotContain(String text)`
    Assert that {text} is not visible on the page.

- `def Wait(int seconds = 10)`
    Wait {10 seconds} for the chained closure to become true.

    Example: `Wait().until(buildCondition {el.getAttribute("value") == '150.00'})`

- `static def buildCondition(Closure code)`
    Wraps a closure ready for use by Wait().

    Example: `Wait().until(buildCondition {el.getAttribute("value") == '150.00'})`

SeleniumTest takes a screenshot of every test on success and failure.
The screenshots are written to the directory specified by property
`screenshot.dir` (default /tmp/sellotape-screenshots).

### Configurable properties

- selenium.driver
    Currently allows `firefox` (default) or `phantomjs`.
- screenshot.dir
    Directory for output screenshots (default `/tmp/sellotape-screenshots`).

### Example

```groovy
package net.ulfheim.sellotape.examples

import net.ulfheim.sellotape.SeleniumTest
import org.openqa.selenium.Keys


class GoogleTest extends SeleniumTest {
    void testSearch() {
        // go to site
        driver.get('http://www.google.com')

        // type search into input
        waitForSelector('input[type="text"]').sendKeys('xyz' + Keys.ENTER)

        // wait only 1 second for element to exist (default wait is 10 seconds)
        waitForText('results', 1)

        // clear the search at top of page
        waitForXpath('//input[@type="text"]').clear()

        pageShouldNotContain('results')
    }
}
```
