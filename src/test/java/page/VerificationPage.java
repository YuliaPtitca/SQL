package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorMessageInvalidCode =  $("[data-test-id=error-notification] .notification__content");
    private SelenideElement errorMessageOverLimitedCode = $("[data-test-id=error-notification] .notification__content");


    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void errorMessageTypeOne() {
        errorMessageInvalidCode.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }

    public void errorMessageTypeTwo() {
        errorMessageOverLimitedCode.shouldBe(visible).shouldHave(text("Ошибка! Превышено количество попыток ввода кода!"));
    }

    public DashboardPage validCode(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidCode(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        errorMessageTypeOne();
    }

    public void invalidCodeOverLimit(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        codeField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        codeField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        errorMessageTypeTwo();
    }
}