package org.example.repozytoria;

import org.example.valueobjects.Przystanek;
import org.example.valueobjects.Trasa;

import java.util.ArrayList;
import java.util.List;

public class RepozytoriumTras implements Repozytorium<Trasa> {
    private final List<Trasa> rejestrTras;

    public RepozytoriumTras() {
        rejestrTras = new ArrayList<>();
    }
    @Override
    public Trasa wyszukaj(String nazwa) {
        for (Trasa trasa : rejestrTras) {
            if (trasa.getNazwa().equals(nazwa)) return trasa;
        }
        return null;
    }

    @Override
    public Trasa dodaj(Trasa element) {
        if(!rejestrTras.contains(element)) {
            rejestrTras.add(element);
        }
        return element;
    }
    public List<Trasa> wyszukajZPrzystankiem(Przystanek przystanek) {
        return rejestrTras.stream()
                .filter(trasa -> trasa.getPrzystanki().contains(przystanek))
                .toList();
    }
}
