package org.example.repozytoria;

import org.example.valueobjects.Przystanek;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RepozytoriumPrzystankowTest {

    static private RepozytoriumPrzystankow SUT;
    static private final List<Przystanek> przystanekList = List.of(new Przystanek("przystanek1", new Point2D.Double(1.0, 1.0)), new Przystanek("przystanek2", new Point2D.Double(2.0, 1.0)));

    @BeforeAll
    static void setUp() {
        SUT = new RepozytoriumPrzystankow();
    }

    public static Stream<Przystanek> przystanekStream() {
        return przystanekList.stream();
    }

    @ParameterizedTest
    @MethodSource("przystanekStream")
    @Order(2)
    void wyszukaj(Przystanek przystanek) {
        //GIVEN
        String nazwa = przystanek.getNazwa();
        //WHEN
        Przystanek actual = SUT.wyszukaj(nazwa);
        //THEN
        assertEquals(przystanek, actual);
    }

    @ParameterizedTest
    @MethodSource("przystanekStream")
    @Order(1)
    void dodaj(Przystanek przystanek) {
        //GIVEN
        //WHEN
        SUT.dodaj(przystanek);
        //THEN
        assertEquals(przystanek, SUT.wyszukaj(przystanek.getNazwa()));
    }
}