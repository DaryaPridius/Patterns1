package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.beans.PropertyEditor;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {
        int addFirstDate = 3;
        String firstDate = DataGenerator.generateDate(addFirstDate);
        int addSecondDate = 7;
        String secondDate = DataGenerator.generateDate(addSecondDate);


        $("[data-test-id='city'] input").setValue(DataGenerator.generateCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.generatePhone());
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstDate));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondDate);
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification]")
                .shouldBe(visible)
                .shouldHave(text("Необходимо подтверждение"));
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondDate));
    }


}
