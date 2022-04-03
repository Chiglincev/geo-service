package ru.netology.geo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.geo.GeoServiceImpl.MOSCOW_IP;
import static ru.netology.geo.GeoServiceImpl.NEW_YORK_IP;

public class GeoServiceImplTest {
    private static GeoServiceImpl geoService;

    @BeforeAll
    public static void beforeAllMethod() {
        geoService = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("testSource")
    public void test_byIP_success(String ip, Country country) {
        assertEquals(geoService.byIp(ip).getCountry(), country);
    }

    @Test
    public void test_byIP_otherCountry() {
        String ip = "88.123.56.11";
        Location expectedLocation = null;

        Location location = geoService.byIp(ip);

        assertNull(location);
    }

    private static Stream<Arguments> testSource() {
        return Stream.of(
                Arguments.of(MOSCOW_IP, Country.RUSSIA),
                Arguments.of(NEW_YORK_IP, Country.USA),
                Arguments.of("172.123.56.11", Country.RUSSIA),
                Arguments.of("96.123.56.11", Country.USA)
        );
    }
}
