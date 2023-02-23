package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

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

    public DashboardPage validTransfer(String cardNum, String amount) {
        sumField.setValue(amount);
        fromField.setValue(cardNum);
        transferButton.click();
        return new DashboardPage();
    }

    public void unsuccessfulTransfer(String cardNum, String amount) {
        sumField.setValue(amount);
        fromField.setValue(cardNum);
        transferButton.click();
        errorNotification.shouldBe(Condition.visible);
    }

    public void cancelTransfer() {
        cancelButton.click();
    }
}
