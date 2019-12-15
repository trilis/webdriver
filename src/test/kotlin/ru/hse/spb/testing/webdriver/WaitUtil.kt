package ru.hse.spb.testing.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait


fun checkLoaded(driver: WebDriver, by: By) {
    val wait = WebDriverWait(driver, 10)
    wait.until(ExpectedConditions.visibilityOfElementLocated(by))
}