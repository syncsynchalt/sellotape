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
