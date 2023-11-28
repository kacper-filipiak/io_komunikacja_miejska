package org.example;

import org.example.repozytoria.RepozytoriumPojazdow;
import org.example.repozytoria.RepozytoriumPrzystankow;
import org.example.repozytoria.RepozytoriumRozkladow;
import org.example.repozytoria.RepozytoriumTras;

public class Factory {
    static public RepozytoriumPrzystankow createRepozytoriumPrzystankow() {return new RepozytoriumPrzystankow();}
    static public RepozytoriumTras createRepozytoriumTras() { return new RepozytoriumTras(); }
    static public RepozytoriumRozkladow createRepozytoriumRozkladow() { return new RepozytoriumRozkladow(); }
    static public RepozytoriumPojazdow createRepozytoriumPojazdow() { return new RepozytoriumPojazdow(); }
}
