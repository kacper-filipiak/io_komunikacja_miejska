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
    private final RepozytoriumPrzystankow repozytoriumPrzystankow;
    private final RepozytoriumTras repozytoriumTras;
    private final RepozytoriumRozkladow repozytoriumRozkladow;
    private final RepozytoriumPojazdow repozytoriumSprawnychPojazdow;
    private final RepozytoriumPojazdow repozytoriumNiesprawnychPojazdow;

    Facade() {
        repozytoriumPrzystankow = Factory.createRepozytoriumPrzystankow();
        repozytoriumTras = Factory.createRepozytoriumTras();
        repozytoriumRozkladow = Factory.createRepozytoriumRozkladow();
        repozytoriumSprawnychPojazdow = Factory.createRepozytoriumPojazdow();
        repozytoriumNiesprawnychPojazdow = Factory.createRepozytoriumPojazdow();
    }

    Facade(RepozytoriumPrzystankow _repozytoriumPrzystankow, RepozytoriumTras _repozytoriumTras, RepozytoriumRozkladow _repozytoriumRozkladow, RepozytoriumPojazdow _repozytoriumSprawnychPojazdow, RepozytoriumPojazdow _repozytoriumNiesprawnychPojazdow) {
        repozytoriumPrzystankow = _repozytoriumPrzystankow;
        repozytoriumTras = _repozytoriumTras;
        repozytoriumRozkladow = _repozytoriumRozkladow;
        repozytoriumSprawnychPojazdow = _repozytoriumSprawnychPojazdow;
        repozytoriumNiesprawnychPojazdow = _repozytoriumNiesprawnychPojazdow;
    }

    List<Date> sprawdzGodzinyOdjazdowDlaPrzystanku(String[] dane) {
        Przystanek przystanek = repozytoriumPrzystankow.wyszukaj(dane[0]);
        if (przystanek == null) throw new IllegalArgumentException("Przystanek nie istnieje");
        List<Trasa> trasy = repozytoriumTras.wyszukajZPrzystankiem(przystanek);
        List<Date> czasyOdjazdu = new ArrayList<Date>();
        for (Trasa trasa : trasy) {
            List<Rozklad> rozklady = repozytoriumRozkladow.wyszukajZTrasa(trasa);
            for (Rozklad rozklad : rozklady) {
                czasyOdjazdu.add(rozklad.getCzasDotarciaNaPrzystanek(przystanek));
            }
        }
        return czasyOdjazdu;
    }

    void dodajPrzystanek(String[] dane) {
        Przystanek przystanek = repozytoriumPrzystankow.wyszukaj(dane[0]);
        if (przystanek != null) throw new IllegalArgumentException("Przystanek już istnieje");
        repozytoriumPrzystankow.dodaj(new Przystanek(dane[0], new Point2D.Double(Double.parseDouble(dane[1]), Double.parseDouble(dane[2]))));
    }

    void utworzTrase(String[] dane) {
        Trasa trasa = repozytoriumTras.wyszukaj(dane[0]);
        if (trasa != null) throw new IllegalArgumentException("Trasa już istnieje");
        int liczbaPrzystankow = Integer.parseInt(dane[1]);
        List<Przystanek> przystanki = new ArrayList<Przystanek>();
        for (int i = 0; i < liczbaPrzystankow; i++) {
            Przystanek przystanek = repozytoriumPrzystankow.wyszukaj(dane[2+i]);
            if (przystanek == null) throw new IllegalArgumentException("Przystanek " + dane[2+i] + " nie istnieje");
            przystanki.add(przystanek);
        }
        repozytoriumTras.dodaj(new Trasa(dane[0], przystanki));
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
        Pojazd pojazd = repozytoriumSprawnychPojazdow.wyszukaj(dane[0]);
        if (pojazd == null) {
            pojazd = repozytoriumNiesprawnychPojazdow.wyszukaj(dane[0]);
            if (pojazd == null) throw new IllegalArgumentException("Pojazd nie istnieje");
            else throw new IllegalArgumentException("Pojazd jest niesprawny");
        }
        Rozklad rozklad = repozytoriumRozkladow.wyszukaj(dane[1]);
        if (rozklad == null) throw new IllegalArgumentException("Rozklad nie istnieje");
        rozklad.setPojazd(pojazd);
    }

    void usunPojazdZRozkladu(String[] dane) {
        Rozklad rozklad = repozytoriumRozkladow.wyszukaj(dane[0]);
        if (rozklad == null) throw new IllegalArgumentException("Rozklad nie istnieje");
        rozklad.setPojazd(null);
    }

    void zarejestrujPojazd(String[] dane) {
        Pojazd pojazd = repozytoriumSprawnychPojazdow.wyszukaj(dane[0]);
        if (pojazd != null) throw new IllegalArgumentException("Pojazd juz istnieje");
        pojazd = repozytoriumNiesprawnychPojazdow.wyszukaj(dane[0]);
        if (pojazd != null) throw new IllegalArgumentException("Pojazd juz istnieje");
        if (dane[1].equals("Sprawny")) repozytoriumSprawnychPojazdow.dodaj(new Pojazd(dane[0], Pojazd.StatusPojazdu.Sprawny));
        else repozytoriumNiesprawnychPojazdow.dodaj(new Pojazd(dane[0], Pojazd.StatusPojazdu.Niesprawny));
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
        pojazd.addHistoryEntry(data, dane[3]);
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
        pojazd.addHistoryEntry(data, dane[3]);
    }

    String sprawdzStanPojazdu(String[] dane) {
        Pojazd pojazd = repozytoriumSprawnychPojazdow.wyszukaj(dane[0]);
        if (pojazd == null) {
            pojazd = repozytoriumNiesprawnychPojazdow.wyszukaj(dane[0]);
            if (pojazd == null) throw new IllegalArgumentException("Pojazd nie istnieje");
        }
        return pojazd.getStatus().toString();
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
