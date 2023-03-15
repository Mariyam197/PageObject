package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("h2");
    private SelenideElement sumField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public TransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage validTransfer(String amount, String cardInfo) {
        makeTransfer(amount, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amount, String cardInfo) {
        sumField.setValue(amount);
        fromField.setValue(cardInfo);
        transferButton.click();
    }

    public void unsuccessfulTransfer(String expectedText) {
        errorNotification.shouldHave(Condition.exactText(expectedText)).shouldBe(Condition.visible);
    }

    public void cancelTransfer() {
        cancelButton.click();
    }
}
