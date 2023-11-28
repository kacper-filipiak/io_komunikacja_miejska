package org.example;

import org.example.repozytoria.RepozytoriumPojazdow;
import org.example.repozytoria.RepozytoriumPrzystankow;
import org.example.repozytoria.RepozytoriumRozkladow;
import org.example.repozytoria.RepozytoriumTras;
import org.example.valueobjects.Pojazd;
import org.example.valueobjects.Przystanek;
import org.example.valueobjects.Rozklad;
import org.example.valueobjects.Trasa;
import org.apache.commons.lang3.time.DateUtils;
import java.awt.geom.Point2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Facade {
    private RepozytoriumPrzystankow repozytoriumPrzystankow;
    private RepozytoriumTras repozytoriumTras;
    private RepozytoriumRozkladow repozytoriumRozkladow;
    private RepozytoriumPojazdow repozytoriumSprawnychPojazdow;
    private RepozytoriumPojazdow repozytoriumNiesprawnychPojazdow;

    List<Date> sprawdzGodzinyOdjazdowDlaPrzystanku(String[] dane) {
        return null;
    }

    void dodajPrzystanek(String[] dane) {
    }

    void utworzTrase(String[] dane) {
    }

    void utworzRozklad(String[] dane) throws ParseException {
        Rozklad rozklad = repozytoriumRozkladow.wyszukaj(dane[0]);
        if (rozklad != null) {
            throw new IllegalArgumentException("Rozklad o podanej nazwie istnieje");
        }
        Trasa trasa = repozytoriumTras.wyszukaj(dane[1]);
        if (trasa == null) {
            throw new IllegalArgumentException("Trasa o podanej nazwie nie istnieje");
        }
        Przystanek przystanek = repozytoriumPrzystankow.wyszukaj(dane[2]);
        if (!trasa.front().equals(przystanek) && !trasa.back().equals(przystanek)) {
            throw new IllegalArgumentException("Przystanek nie jest początkiem trasy");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        Date data = formatter.parse(dane[3]);
        Map<String, Date> czasyDojazdu = this.obliczCzasyDojazduNaPrzytanki(trasa, przystanek, data);
        rozklad = new Rozklad(trasa, czasyDojazdu, przystanek, dane[0]);
        repozytoriumRozkladow.dodaj(rozklad);
    }

    void dodajPojazdDoRozkladu(String[] dane) {
    }

    void usunPojazdZRozkladu(String[] dane) {
    }

    void zarejestrujPojazd(String[] dane) {
    }

    void zglosAwarie(String[] dane) throws ParseException {
        Pojazd pojazd = repozytoriumSprawnychPojazdow.wyszukaj(dane[0]);
        if(pojazd == null) {
            pojazd = repozytoriumNiesprawnychPojazdow.wyszukaj(dane[0]);
            if (pojazd == null) {
                throw new IllegalArgumentException("Pojazd o danej nazwie nie znaleziony");
            }
        } else {
            if(Objects.equals(dane[1], "Niesprawny")) {
                repozytoriumSprawnychPojazdow.usun(pojazd);
                repozytoriumNiesprawnychPojazdow.dodaj(pojazd);
                List<Rozklad> rozklady = repozytoriumRozkladow.wyszukajZPojazdem(pojazd);
                rozklady.forEach(rozklad -> rozklad.setPojazd(null));
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date data = formatter.parse(dane[2]);
        pojazd.setStatus( Pojazd.StatusPojazdu.valueOf(dane[1]));
    }

    void zglosNaprawe(String[] dane) throws ParseException {
        Pojazd pojazd = repozytoriumNiesprawnychPojazdow.wyszukaj(dane[0]);
        if(pojazd == null) {
            pojazd = repozytoriumSprawnychPojazdow.wyszukaj(dane[0]);
            if (pojazd == null) {
                throw new IllegalArgumentException("Pojazd o danej nazwie nie znaleziony");
            }
        } else {
            if(Objects.equals(dane[1], "Sprawny")) {
                repozytoriumNiesprawnychPojazdow.usun(pojazd);
                repozytoriumSprawnychPojazdow.dodaj(pojazd);
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date data = formatter.parse(dane[2]);
        pojazd.setStatus( Pojazd.StatusPojazdu.valueOf(dane[1]));
    }

    String[] sprawdzStanPojazdu(String[] dane) {
        return null;
    }

    private Map<String, Date> obliczCzasyDojazduNaPrzytanki(Trasa trasa, Przystanek przystanekPoczatkowy, Date czasOdjazdu) {
        double przedkosc = 10.0;
        List<Przystanek> przystanki = new ArrayList(trasa.getPrzystanki());
        if(trasa.back().equals(przystanekPoczatkowy)) {
            Collections.reverse(przystanki);
        } else if (!trasa.back().equals(przystanekPoczatkowy) && !trasa.front().equals(przystanekPoczatkowy)) {
            throw new IllegalArgumentException("Przystanek nie jest początkiem trasy");
        }
        Date czas = new Date();
        czas = czasOdjazdu;
        new Point2D.Double();
        Point2D poprzedniaLokalizacja;
        poprzedniaLokalizacja = przystanekPoczatkowy.getLokalizacja();
        Map<String, Date> mapaCzasow = new HashMap<>();
        mapaCzasow.put(przystanekPoczatkowy.getNazwa(), czas);
        for(int i = 1; i < przystanki.size(); i++) {
            czas = DateUtils.addSeconds(czas, (int)(poprzedniaLokalizacja.distance(przystanki.get(i).getLokalizacja()) * przedkosc));
            mapaCzasow.put(przystanki.get(i).getNazwa(), czas);
            poprzedniaLokalizacja = przystanki.get(i).getLokalizacja();
        }
        return mapaCzasow;
    }
}
