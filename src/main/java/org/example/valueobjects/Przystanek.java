package org.example.valueobjects;

import java.awt.geom.Point2D;

public class Przystanek {
    private String nazwa;
    private Point2D lokalizacja;


    public Przystanek(String nazwa, Point2D lokalizacja) {
        this.nazwa = nazwa;
        this.lokalizacja = lokalizacja;
    }

    public Point2D getLokalizacja() {
        return lokalizacja;
    }

    public String getNazwa() {
        return nazwa;
    }
}