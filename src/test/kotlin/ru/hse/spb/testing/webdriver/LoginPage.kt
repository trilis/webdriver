package ru.hse.spb.testing.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

object LoginPage {
    private const val loginId = "id_l.L.login"
    private const val passwordId = "id_l.L.password"
    private const val buttonId = "id_l.L.loginButton"

    fun login(driver: WebDriver, login: String, password: String) {
        checkLoaded(driver, By.id(loginId))

        val loginInput = driver.findElement(By.id(loginId))
        val passwordInput = driver.findElement(By.id(passwordId))
        val loginButton = driver.findElement(By.id(buttonId))

        loginInput.sendKeys(login)
        passwordInput.sendKeys(password)

        loginButton.click()
    }
}
