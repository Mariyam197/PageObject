package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    DashboardPage dashboardPage;
    int amount;
    int balanceCard1;
    int balanceCard2;
    int endBalanceCard1;
    int endBalanceCard2;


    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        balanceCard1 = dashboardPage.getCardBalance(0);
        balanceCard2 = dashboardPage.getCardBalance(1);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        amount = 100;
        var transferPage = dashboardPage.transferPage(0);
        var cardNum = DataHelper.getSecondCard().getCardNumber();
        var validTransfer = transferPage.validTransfer(cardNum, Integer.toString(amount));
        endBalanceCard1 = dashboardPage.getCardBalance(0);
        endBalanceCard2 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(balanceCard1 + amount, endBalanceCard1);
        Assertions.assertEquals(balanceCard2 - amount, endBalanceCard2);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        amount = 500;
        var transferPage = dashboardPage.transferPage(1);
        var cardNum = DataHelper.getFirstCard().getCardNumber();
        var validTransfer = transferPage.validTransfer(cardNum, Integer.toString(amount));
        endBalanceCard1 = dashboardPage.getCardBalance(0);
        endBalanceCard2 = dashboardPage.getCardBalance(1);
        Assertions.assertEquals(balanceCard1 - amount, endBalanceCard1);
        Assertions.assertEquals(balanceCard2 + amount, endBalanceCard2);
    }

}
