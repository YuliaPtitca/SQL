package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {

    @AfterAll
    static void shouldCleanDataBase() {
        DataHelper.cleanerDataBase();
    }

    @Test
    void shouldAuthorizationFirstUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getAuthInfoForFirstUser();
        var verificationPage = loginPage.validInfo(authInfo);
        var verifyInfo = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validCode(verifyInfo);
        dashboardPage.check();
    }

    @Test
    void shouldAuthorizationSecondUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getAuthInfoForSecondUser();
        var verificationPage = loginPage.validInfo(authInfo);
        var verifyInfo = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validCode(verifyInfo);
        dashboardPage.check();
    }

    @Test
    void shouldAuthorizationWithInvalidPassword() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getInvalidPassword("en");
        loginPage.invalidInfo(authInfo);
    }

    @Test
    void shouldAuthorizationWithInvalidLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getInvalidLogin("en");
        loginPage.invalidInfo(authInfo);
    }

    @Test
    void shouldAuthorizationWithInvalidPasswordForThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getInvalidPassword("en");
        loginPage.blockedSystem(authInfo);
    }

    @Test
    void shouldAuthorizationWithInvalidCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getAuthInfoForSecondUser();
        var verificationPage = loginPage.validInfo(authInfo);
        var verifyInfo = DataHelper.getInvalidCode();
        verificationPage.invalidCode(verifyInfo);
    }

    @Test
    void shouldAuthorizationWithInvalidCodeForThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Configuration.holdBrowserOpen = true;
        var authInfo = DataHelper.getAuthInfoForSecondUser();
        var verificationPage = loginPage.validInfo(authInfo);
        var verifyInfo = DataHelper.getInvalidCode();
        verificationPage.invalidCodeOverLimit(verifyInfo);
    }
}