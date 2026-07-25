import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CinemaTests {

    @Test
    void test01_simple_cinema() {
        open("https://slqamsk.github.io/cases/cinema/v02");
        $("input[name=age]").setValue("35");
        String dateValue = "28.07.2026"; // Дата в формате YYYY-MM-DD
        $("input[name='date']").setValue(dateValue);
        $x("//input[@name='session' and @value='14:00']").click();
        $("input[name=film][value=crime]").click();
        $x("//button[@type='submit']").click();
        $x("//div[@id='result']").shouldHave(text("Стоимость билета: 400 рублей."));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "data.csv", numLinesToSkip = 1)
    void test02_param_cinema(String age, String dateValue, String start, String film, String priceMessage) {
        open("https://slqamsk.github.io/cases/cinema/");
        $("input[name=age]").setValue(age);
        $("input[name='date']").setValue(dateValue); // Дата в формате YYYY-MM-DD
        $("input[name=session][value='" + start + "']").click();
        $("input[name=film][value=" + film + "]").click();
        $("input[type=submit").click();
        $("div").shouldHave(text(priceMessage));
        sleep(1_000);
    }

    static String getDateByWeekDay(int weekDay) {
        // Приводим к стандарту Java (1-7 = понедельник-воскресенье)
        DayOfWeek targetDay = DayOfWeek.of(weekDay);

        // Получаем текущую дату
        LocalDate today = LocalDate.now();

        // Находим ближайшую дату с нужным днём недели, начиная со следующего дня
        LocalDate resultDate = today.plusDays(1).with(TemporalAdjusters.nextOrSame(targetDay));

        // Форматируем в строку
        return resultDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }



    static Stream<Arguments> provideCinemaData() {
        Stream.Builder<Arguments> argumentsBuilder = Stream.builder();

        argumentsBuilder.add(Arguments.of("35", getDateByWeekDay(2), "1", "back", "Стоимость билета: 350 рублей."));
        argumentsBuilder.add(Arguments.of("20", getDateByWeekDay(1), "2", "crime", "Стоимость билета: 350 рублей."));
        argumentsBuilder.add(Arguments.of("5", getDateByWeekDay(3), "3", "king", "Стоимость билета: 100 рублей."));

        return argumentsBuilder.build();
    }

    @ParameterizedTest
    @MethodSource("provideCinemaData")
    void test03_source_cinema(String age, String dateValue, String start, String film, String priceMessage) {
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
        $("input[name=age]").setValue(age);
        $("input[name='date']").setValue(dateValue); // Дата в формате YYYY-MM-DD
        $("input[name=session][value='" + start + "']").click();
        $("input[name=film][value=" + film + "]").click();
        $("input[type=submit").click();
        $("div").shouldHave(text(priceMessage));
        sleep(1_000);
    }
}
