package ru.hse.spb.testing.webdriver

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class NewUserLoginTest {

    private fun testSuccess(login: String) {
        UsersPage.createNewUser(driver, login, userDefaultPassword)

        try {
            val wait = WebDriverWait(driver, 10)
            wait.until(ExpectedConditions.titleIs("User: $login"))
        } catch (e: TimeoutException) {
            UsersPage.closeNewUserWindow(driver)
            fail("User $login creation was not successful")
        }

        UserEditPage.getBackToUsersPage(driver)
    }

    private fun testFail(login: String, expectedFailMessage: String) {
        UsersPage.createNewUser(driver, login, userDefaultPassword)
        val popupText = UsersPage.findPopupText(driver)
        if (popupText == null) {
            UserEditPage.getBackToUsersPage(driver)
        } else {
            UsersPage.closeNewUserWindow(driver)
        }
        assertEquals(expectedFailMessage, popupText)
    }

    @Test
    fun testShortName() {
        testSuccess("a")
    }

    @Test
    fun testSimpleName() {
        testSuccess("login")
    }

    @Test
    fun testNameWithDigits() {
        testSuccess("aA98")
    }

    @Test
    fun testStartingWithDigit() {
        testSuccess("123")
    }

    @Test
    fun testMany() {
        testSuccess("a")
        testSuccess("b")
        testSuccess("c")
        testSuccess("d")
        testSuccess("e")
    }

    @Test
    fun testDuplicate() {
        testSuccess("login")
        testFail("login", expectedDuplicateError)
    }

    // current message for 'root' and 'guest' name duplicates is
    // "Removing null is prohibited",
    // which is definitely non-informative
    @Test
    fun testRootName() {
        testFail("root", expectedDuplicateError)
    }

    @Test
    fun testGuestName() {
        testFail("guest", expectedDuplicateError)
    }

    @Test
    fun testSpecialSymbols() {
        testFail("</html>", expectedSpecialSymbolsError)
    }

    @Test
    fun testEmpty() {
        UsersPage.createNewUser(driver, "", userDefaultPassword)
        val tooltipText = UsersPage.findErrorTooltipText(driver)
        if (tooltipText == null && UsersPage.findPopupText(driver) == null) {
            UserEditPage.getBackToUsersPage(driver)
        } else {
            UsersPage.closeNewUserWindow(driver)
        }
        assertEquals(expectedEmptyTooltipText, tooltipText)
    }

    @AfterEach
    fun deleteUsers() {
        UsersPage.deleteNewUsers(driver)
    }

    companion object {

        private const val url = "http://localhost:8080"
        private const val login = "root"
        private const val password = "12345678"
        private const val userDefaultPassword = "qwerty"
        private lateinit var driver: WebDriver

        private const val expectedDuplicateError =
            "Value should be unique: login"
        private const val expectedSpecialSymbolsError =
            "login shouldn't contain characters \"<\", \"/\", \">\": login"
        private const val expectedEmptyTooltipText =
            "Login is required!"

        @BeforeAll
        @JvmStatic
        fun loadUsersPage() {
            System.setProperty("webdriver.chrome.driver", "/opt/WebDriver/bin/chromedriver")
            driver = ChromeDriver()
            driver.get(url)
            LoginPage.login(driver, login, password)
            MainPage.goToUsersPage(driver)
        }

        @AfterAll
        @JvmStatic
        fun quit() {
            driver.quit()
        }
    }
}