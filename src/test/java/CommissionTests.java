import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CommissionTests {

    @ParameterizedTest
    @ValueSource(strings = {"100", "999.99", "1000", "1000.01"})
    void test01(String sum) {
        open("https://slqa.ru/cases/fc/v01/");
        $x("//input[@name='sum']").setValue(sum);
        sleep(2000);
        $x("//input[@type='submit']").click();
        sleep(2000);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "com_test.csv", numLinesToSkip=1)
    void test02Positive(String sum, String com) {
        open("https://slqa.ru/cases/fc/v01/");
        $x("//input[@name='sum']").setValue(sum);
        $x("//input[@type='submit']").click();
        $x("//span[@name='com']").shouldHave(text(com));
    }

}
