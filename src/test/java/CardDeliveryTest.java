package ru.netology.javaqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    private String generateDate() {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldBeSuccessfullyCompleted() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ростов-на-Дону");
        String planningDate = generateDate();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Красных Наталья");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + planningDate));
    }
}