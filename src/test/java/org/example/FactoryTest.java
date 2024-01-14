package org.example;

import org.example.repozytoria.RepozytoriumPojazdow;
import org.example.repozytoria.RepozytoriumPrzystankow;
import org.example.repozytoria.RepozytoriumRozkladow;
import org.example.repozytoria.RepozytoriumTras;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactoryTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createRepozytoriumPrzystankow() {
        //GIVEN
        //WHEN
        RepozytoriumPrzystankow actual = Factory.createRepozytoriumPrzystankow();
        //THEN
        assertNotNull(actual);
        assertInstanceOf(RepozytoriumPrzystankow.class, actual);
    }

    @Test
    void createRepozytoriumTras() {
        //GIVEN
        //WHEN
        RepozytoriumTras actual = Factory.createRepozytoriumTras();
        //THEN
        assertNotNull(actual);
        assertInstanceOf(RepozytoriumTras.class, actual);
    }

    @Test
    void createRepozytoriumRozkladow() {
        //GIVEN
        //WHEN
        RepozytoriumRozkladow actual = Factory.createRepozytoriumRozkladow();
        //THEN
        assertNotNull(actual);
        assertInstanceOf(RepozytoriumRozkladow.class, actual);
    }

    @Test
    void createRepozytoriumPojazdow() {
        //GIVEN
        //WHEN
        RepozytoriumPojazdow actual = Factory.createRepozytoriumPojazdow();
        //THEN
        assertNotNull(actual);
        assertInstanceOf(RepozytoriumPojazdow.class, actual);
    }
}