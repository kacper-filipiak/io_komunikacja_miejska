package org.example.repozytoria;

import org.example.valueobjects.Pojazd;
import org.example.valueobjects.Rozklad;
import org.example.valueobjects.Trasa;

import java.util.ArrayList;
import java.util.List;

public class RepozytoriumRozkladow implements Repozytorium<Rozklad> {
    private final List<Rozklad> rejestrRozkladow;
    public RepozytoriumRozkladow(){
        rejestrRozkladow = new ArrayList<>();
    }
    @Override
    public Rozklad wyszukaj(String nazwa) {
        for (Rozklad rozklad : rejestrRozkladow) {
            if (rozklad.getNazwa().equals(nazwa)) return rozklad;
        }
        return null;
    }

    @Override
    public Rozklad dodaj(Rozklad element) {
        if(!rejestrRozkladow.contains(element)){
            rejestrRozkladow.add(element);
        }
        return element;
    }
    public List<Rozklad> wyszukajZPojazdem(Pojazd pojazd) {
        return rejestrRozkladow.stream()
                .filter(rozklad -> rozklad.getPojazd().equals(pojazd))
                .toList();
    }

    public List<Rozklad> wyszukajZTrasa(Trasa trasa) {
        return rejestrRozkladow.stream()
                .filter(rozklad -> rozklad.getTrasa().equals(trasa))
                .toList();
    }

}
