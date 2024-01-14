package org.example.repozytoria;

import org.example.valueobjects.Pojazd;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepozytoriumPojazdow implements Repozytorium<Pojazd> {
    private final List<Pojazd> rejestrPojazdow;

    public RepozytoriumPojazdow(){
        rejestrPojazdow = new ArrayList<>();
    }

    @Override
    public Pojazd wyszukaj(String nazwa) {
        return rejestrPojazdow.stream()
                .filter(x-> Objects.equals(x.getIdentyfikator(), nazwa))
                .findFirst().orElse(null);
    }

    @Override
    public Pojazd dodaj(Pojazd element) {
        if(!rejestrPojazdow.contains(element)) {
            rejestrPojazdow.add(element);
        }
        return element;
    }

    public Boolean usun(Pojazd pojazd) {
        return rejestrPojazdow.remove(pojazd);
    }
}
