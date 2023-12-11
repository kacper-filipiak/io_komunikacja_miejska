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

    public String getNazwa() { return nazwa; }

    public Trasa getTrasa() { return trasa; }
    public Pojazd getPojazd() {
        return pojazd;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    public Date getCzasDotarciaNaPrzystanek (Przystanek przystanek) {
        return czasyDotarciaNaPrzystanki.get(przystanek.getNazwa());
    }
    public Date getCzasDotarciaNaPrzystanek (String przystanek) {
        return czasyDotarciaNaPrzystanki.get(przystanek);
    }
}
