package ru.hse.spb.testing.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

object UserEditPage {
    private const val backToUsersLinkXpath =
        "//*[@id=\"id_l.E.AdminBreadcrumb.AdminBreadcrumb\"]/ul/li[1]/a"

    fun getBackToUsersPage(driver: WebDriver) {
        checkLoaded(driver, By.xpath(backToUsersLinkXpath))

        val backToUsersLink =
            driver.findElement(By.xpath(backToUsersLinkXpath))
        backToUsersLink.click()

    }
}