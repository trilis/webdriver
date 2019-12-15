package ru.hse.spb.testing.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

object MainPage {

    private const val settingsButtonId = "ring-font-icon_cog"
    private const val usersLinkText = "Users"

    fun goToUsersPage(driver: WebDriver) {
        checkLoaded(driver, By.className(settingsButtonId))

        val settingsButton = driver.findElement(By.className(settingsButtonId))
        settingsButton.click()

        checkLoaded(driver, By.linkText(usersLinkText))

        val usersLink = driver.findElement(By.linkText(usersLinkText))
        usersLink.click()
    }
}