package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

import static ru.netology.entity.Country.*;

public class LocalizationServiceImplTest {

    @Test
    public void test_LocalizationServiceImpl_success() {
        LocalizationServiceImpl service = new LocalizationServiceImpl();
        Country country = RUSSIA;
        Country countryUSA = USA;
        Country foreignCountry = BRAZIL;
        String expectedMessage = "Добро пожаловать";
        String expectedMessageForeign = "Welcome";

        String resultRussia = service.locale(country);
        String resultUSA = service.locale(countryUSA);
        String resultForeign = service.locale(foreignCountry);

        Assertions.assertEquals(resultRussia, expectedMessage);
        Assertions.assertEquals(resultUSA, expectedMessageForeign);
        Assertions.assertEquals(resultForeign, expectedMessageForeign);
    }
}
