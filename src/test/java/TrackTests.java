import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class TrackTests {
    @Test
    void test01Authentication(){
        open("https://slqamsk.github.io/cases/sltrack/v02/");
        $(By.id("username")).setValue("admin@example.com");
        $(By.id("password")).setValue("admin123");
        $(By.className("btn")).click();
        $(By.id("greeting")).shouldBe(visible);
        $(By.id("greeting")).shouldHave(text("Привет, "));
        $(By.id("user-name")).shouldHave(text("Пётр Иванов"));
        $(By.id("user-role")).shouldHave(text("Администратор"));
        $(By.id("logout-btn")).shouldBe(visible);
        $(By.id("my-tasks-btn")).click();
        //$(By.className("task-assignee")).shouldHave(text("Пётр Иванов"));

        ElementsCollection allCards = $$x("//div[@class='task-assignee']");

        System.out.println("Найдено всего карточек: " + allCards.size());

        for (SelenideElement el : allCards) {
            System.out.println(el.$x("./..").text());
            el.shouldHave(text("Пётр Иванов"));
        }

    }
}