package ru.hse.spb.testing.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

object UsersPage {
    private const val createNewUserLinkId = "id_l.U.createNewUser"
    private const val loginInputId = "id_l.U.cr.login"
    private const val passwordInputId = "id_l.U.cr.password"
    private const val confirmPasswordId = "id_l.U.cr.confirmPassword"
    private const val okButtonId = "id_l.U.cr.createUserOk"
    private const val closeButtonId = "id_l.U.cr.closeCreateUserDlg"
    private const val groupFilterXpath = "//*[@id=\"id_l.U.groupFilter\"]/a"
    private const val newUsersXpath = "//*[@_cn_=\"id_l.U.groupFilter\"]/ul/li[2]"
    private const val searchButtonId = "id_l.U.searchButton"
    private const val resetButtonId = "id_l.U.resetButton"
    private const val firstDeleteButtonXpath =
        "//*[@id=\"id_l.U.usersList.usersList\"]/table/tbody/tr[1]/td[6]/a[1]"
    private const val bulbClassName = "error-bulb2"
    private const val tooltipClassName = "error-tooltip"

    fun createNewUser(driver: WebDriver, login: String, password: String) {
        checkLoaded(driver, By.id(searchButtonId))

        val createNewUserLink = driver.findElement(By.id(createNewUserLinkId))
        createNewUserLink.click()

        checkLoaded(driver, By.id(loginInputId))

        val loginInput = driver.findElement(By.id(loginInputId))
        val passwordInput = driver.findElement(By.id(passwordInputId))
        val confirmPasswordInput = driver.findElement(By.id(confirmPasswordId))
        val okButton = driver.findElement(By.id(okButtonId))

        loginInput.sendKeys(login)
        passwordInput.sendKeys(password)
        confirmPasswordInput.sendKeys(password)

        okButton.click()
    }

    fun closeNewUserWindow(driver: WebDriver) {
        checkLoaded(driver, By.id(closeButtonId))

        val closeButton = driver.findElement(By.id(closeButtonId))
        closeButton.click()
    }

    fun deleteNewUsers(driver: WebDriver) {
        checkLoaded(driver, By.xpath(groupFilterXpath))

        val groupFilter = driver.findElement(By.xpath(groupFilterXpath))
        groupFilter.click()

        checkLoaded(driver, By.xpath(newUsersXpath))

        val newUsers = driver.findElement(By.xpath(newUsersXpath))
        newUsers.click()

        val searchButton = driver.findElement(By.id(searchButtonId))
        searchButton.click()

        val wait = WebDriverWait(driver, 10)
        wait.until(ExpectedConditions.urlContains("group"))

        while (true) {
            val deleteButtons = driver.findElements(By.xpath(firstDeleteButtonXpath))

            if (deleteButtons.isEmpty()) {
                break
            }

            deleteButtons[0].click()

            wait.until(ExpectedConditions.alertIsPresent())

            driver.switchTo().alert().accept()

            val oldResetButton = driver.findElement(By.id(resetButtonId))
            driver.navigate().refresh()
            wait.until(ExpectedConditions.stalenessOf(oldResetButton))
        }

        val resetButton = driver.findElement(By.id(resetButtonId))
        resetButton.click()
        wait.until(ExpectedConditions.stalenessOf(resetButton))
    }

    fun findPopupText(driver: WebDriver): String? {
        return try {
            checkLoaded(driver, By.className("errorSeverity"))

            val popupText = driver.findElement(By.className("errorSeverity"))

            popupText.text
        } catch (e: TimeoutException) {
            null
        }
    }

    fun findErrorTooltipText(driver: WebDriver): String? {
        return try {
            checkLoaded(driver, By.className(bulbClassName))
            val bulb = driver.findElement(By.className(bulbClassName))
            bulb.click()

            checkLoaded(driver, By.className(tooltipClassName))
            val tooltip = driver.findElement(By.className(tooltipClassName))
            tooltip.text
        } catch (e: TimeoutException) {
            null
        }
    }
}