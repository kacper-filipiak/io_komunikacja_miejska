package org.example.valueobjects;

import java.util.Date;
import java.util.Map;

public class Rozklad {
    private Trasa trasa;
    private Map<String, Date> czasyDotarciaNaPrzystanki;
    private Przystanek przystanekPoczatkowy;
    private String nazwa;
    private Pojazd pojazd;

    public Rozklad(Trasa trasa, Map<String, Date> czasyDotarciaNaPrzystanki, Przystanek przystanekPoczatkowy, String nazwa) {
        this.trasa = trasa;
        this.czasyDotarciaNaPrzystanki = czasyDotarciaNaPrzystanki;
        this.przystanekPoczatkowy = przystanekPoczatkowy;
        this.nazwa = nazwa;
    }

    public Pojazd getPojazd() {
        return pojazd;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }
}
