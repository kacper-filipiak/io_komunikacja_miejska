package org.example.valueobjects;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PojazdTest {

    Pojazd SUT;

    @BeforeEach
    void setUp() {
        SUT = new Pojazd("pojazd_id", Pojazd.StatusPojazdu.Sprawny);
    }

    @Test
    void getHistoria() {
        //GIVEN
        Date time = Date.from(Instant.ofEpochSecond(30000));
        String description = "opis1";
        SUT.addHistoryEntry(time, description);
        //WHEN
        Map<Date, String> actual = SUT.getHistoria();
        //THEN
        assertEquals(Map.of(time, description), actual);
    }

    @Test
    void getStatus() {
        //GIVEN
        //WHEN
        //THEN
    }

    @Test
    void getIdentyfikator() {
        //GIVEN
        String expected = "pojazd_id";
        //WHEN
        String actual = SUT.getIdentyfikator();
        //THEN
        assertEquals(expected, actual);
    }

    static Stream<Pojazd.StatusPojazdu> stateProvider() {
        return Stream.of( Pojazd.StatusPojazdu.Sprawny, Pojazd.StatusPojazdu.Niesprawny); }
    @ParameterizedTest()
    @MethodSource("stateProvider")
    void setStatus(Pojazd.StatusPojazdu expected) {
        //GIVEN
        //WHEN
        SUT.setStatus(expected);
        //THEN
        assertEquals(expected, SUT.getStatus());
    }
    @Test
    void addHistoryEntry() {
        //GIVEN
        Date time = Date.from(Instant.ofEpochSecond(30000));
        String description = "opis1";
        //WHEN
        SUT.addHistoryEntry(time, description);
        //THEN
        assertEquals(SUT.getHistoria().get(time), description);
    }
}