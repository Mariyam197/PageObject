package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {
    DashboardPage dashboardPage;



    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);
        var amount = generateValidAmount(secondCardBalance);
        var transferPage = dashboardPage.transferPage(0);
        var cardNum = DataHelper.getSecondCard().getCardNumber();
        var validTransfer = transferPage.validTransfer(Integer.toString(amount), cardNum);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var actualFirstCardBalance = dashboardPage.getCardBalance(0);
        var actualSecondCardBalance = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(expectedBalanceFirstCard,actualFirstCardBalance);
        Assertions.assertEquals(expectedBalanceSecondCard, actualSecondCardBalance);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);
        var amount = generateValidAmount(firstCardBalance);
        var transferPage = dashboardPage.transferPage(1);
        var cardNum = DataHelper.getFirstCard().getCardNumber();
        var validTransfer = transferPage.validTransfer(Integer.toString(amount), cardNum);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var actualFirstCardBalance = dashboardPage.getCardBalance(0);
        var actualSecondCardBalance = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(expectedBalanceFirstCard,actualFirstCardBalance);
        Assertions.assertEquals(expectedBalanceSecondCard, actualSecondCardBalance);
    }

    @Test
    void shouldNotTransferMoreMoneyThanBalance() {
        var firstCardBalance = dashboardPage.getCardBalance(0);
        var secondCardBalance = dashboardPage.getCardBalance(1);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.transferPage(0);
        var cardNum = getSecondCard().getCardNumber();
        var validTransfer = transferPage.validTransfer(Integer.toString(amount), cardNum);
        transferPage.unsuccessfulTransfer("Ошибка!");
        var actualFirstCardBalance = dashboardPage.getCardBalance(0);
        var actualSecondCardBalance = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(firstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance, actualSecondCardBalance);
    }
}
