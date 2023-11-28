package org.example.valueobjects;

import java.util.List;

public class Trasa {
    private String nazwa;
    private List<Przystanek> przystanki;

    public Trasa(String nazwa, List<Przystanek> przystanki) {
        if (przystanki.size() < 2) throw new RuntimeException("Trasa musi mieć minimum 2 przystanki");
        this.nazwa = nazwa;
        this.przystanki = przystanki;
    }

    public String getNazwa() {
        return nazwa;
    }

    public List<Przystanek> getPrzystanki() {
        return przystanki;
    }

    public Przystanek front() {
        return przystanki.get(0);
    }

    public Przystanek back() {
        return przystanki.get(przystanki.size() - 1);
    }
}
