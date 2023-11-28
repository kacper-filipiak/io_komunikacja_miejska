package org.example.repozytoria;

import org.example.valueobjects.Przystanek;

import java.util.List;

public class RepozytoriumPrzystankow implements Repozytorium<Przystanek> {
    private List<Przystanek> rejestrPrzystankow;

    @Override
    public Przystanek wyszukaj(String nazwa) {
        return null;
    }

    @Override
    public Przystanek dodaj(Przystanek element) {
        if(!rejestrPrzystankow.contains(element)) {
            rejestrPrzystankow.add(element);
        }
        return element;
    }
}