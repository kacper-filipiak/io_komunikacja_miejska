package org.example.repozytoria;

import jdk.jshell.spi.ExecutionControl;
import org.example.valueobjects.Pojazd;
import org.example.valueobjects.Przystanek;
import org.example.valueobjects.Rozklad;
import org.example.valueobjects.Trasa;

import java.util.List;

public class RepozytoriumRozkladow implements Repozytorium<Rozklad> {
    private List<Rozklad> rejestrRozkladow;
    @Override
    public Rozklad wyszukaj(String nazwa) {
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
        return null;
    }

}
