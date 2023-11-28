package org.example.repozytoria;

import org.example.valueobjects.Przystanek;
import org.example.valueobjects.Trasa;

import java.util.List;

public class RepozytoriumTras implements Repozytorium<Trasa> {
    private List<Trasa> rejestrTras;

    @Override
    public Trasa wyszukaj(String nazwa) {
        return null;
    }

    @Override
    public Trasa dodaj(Trasa element) {
        if(!rejestrTras.contains(element)) {
            rejestrTras.add(element);
        }
        return element;
    }
    List<Trasa> wyszukajZPrzystankiem(Przystanek przystanek) {
        return null;
    }
}
