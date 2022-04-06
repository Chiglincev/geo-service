package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {

    @ParameterizedTest
    @CsvSource(value = {"172.123.12.19, 96.123.12.19, 84.123.12.19"})
    public void test_send_checkLanguage(String argumentRu, String argumentUS, String argumentGer) {

        String expectedMessageForeign = "Welcome";
        String expectedMessageRu = "Добро пожаловать";

        String messageRu = getMessage(argumentRu);
        String messageUS = getMessage(argumentUS);
        String messageGer = getMessage(argumentGer);

        Assertions.assertEquals(messageRu, expectedMessageRu);
        Assertions.assertEquals(messageUS, expectedMessageForeign);
        Assertions.assertEquals(messageGer, expectedMessageForeign);
    }

    static String getMessage(String argument) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.123.12.19"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        Mockito.when(geoService.byIp("96.123.12.19"))
                .thenReturn(new Location("New York", Country.USA, null,  0));

        Mockito.when(geoService.byIp("84.123.12.19"))
                .thenReturn(new Location("Berlin", Country.GERMANY, null,  0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when((localizationService.locale(Country.RUSSIA)))
                .thenReturn("Добро пожаловать");

        Mockito.when((localizationService.locale(Country.USA)))
                .thenReturn("Welcome");

        Mockito.when((localizationService.locale(Country.GERMANY)))
                .thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, argument);
        return messageSender.send(headers);
    }
}
