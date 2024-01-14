package org.example;

import org.example.repozytoria.RepozytoriumPojazdow;
import org.example.repozytoria.RepozytoriumPrzystankow;
import org.example.repozytoria.RepozytoriumRozkladow;
import org.example.repozytoria.RepozytoriumTras;
import org.example.valueobjects.Pojazd;
import org.example.valueobjects.Przystanek;
import org.example.valueobjects.Rozklad;
import org.jmock.auto.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.mockito.Mockito;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest implements TestExecutionExceptionHandler {

    private final RepozytoriumRozkladow repozytoriumRozkladowMock = Mockito.mock(RepozytoriumRozkladow.class);

    @Mock
    private final RepozytoriumPojazdow repozytoriumSprawnychPojazdowMock = Mockito.mock(RepozytoriumPojazdow.class);

    @Mock
    private final RepozytoriumPojazdow repozytoriumNiesprawnychPojazdowMock = Mockito.mock(RepozytoriumPojazdow.class);

    @Mock
    private final RepozytoriumPrzystankow repozytoriumPrzystankowMock = Mockito.mock(RepozytoriumPrzystankow.class);

    @Mock
    private final RepozytoriumTras repozytoriumTrasMock = Mockito.mock(RepozytoriumTras.class);

    Facade SUT;

    @BeforeEach
    public void setUp() {
        SUT = new Facade();
    }

    @ExtendWith(FacadeTest.class)
    @Test
    @Tag("HappyPath")
    void dodajPrzystanek() {
        //GIVEN
        String[] przystanekData = {"przystanek1", "2.0", "3.0"};
        //WHEN
        //THEN
        assertDoesNotThrow(() -> SUT.dodajPrzystanek(przystanekData));
    }

    @ExtendWith(FacadeTest.class)
    @Test
    @Tag("ThrowsError")
    void dodajPrzystanekDupplikat() {
        //GIVEN
        String[] przystanekData = {"przystanek1", "2.0", "3.0"};
        //WHEN
        SUT.dodajPrzystanek(przystanekData);
        //THEN
        assertThrows(IllegalArgumentException.class, () -> SUT.dodajPrzystanek(przystanekData), "Przystanek już istnieje");
    }

    @ExtendWith(FacadeTest.class)
    @Test
    @Tag("ThrowsError")
    void utworzTraseZNieistniejacegoPrzystanku() {
        //GIVEN
        String[] trasaData = {"przystanek1", "1", "przystanekKtoryNieIstnieje"};
        //WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> SUT.utworzTrase(trasaData), "Przystanek przystanekKtoryNieIstnieje nie istnieje");
    }

    @Test
    void dodajPojazdDoRozkladu() {
        //GIVEN
        Facade MockSUT = new Facade(
                repozytoriumPrzystankowMock,
                repozytoriumTrasMock,
                repozytoriumRozkladowMock,
                repozytoriumSprawnychPojazdowMock,
                repozytoriumNiesprawnychPojazdowMock
        );
        Przystanek przystanekPoczatkowy = new Przystanek("przystanek1", new Point2D.Double(2.0, 3.0));
        Przystanek przystanekKoncowy = new Przystanek("przystanek2", new Point2D.Double(3.0, 3.0));
        Rozklad rozklad = Mockito.mock(Rozklad.class);
        Pojazd pojazd = new Pojazd("pojazd1", Pojazd.StatusPojazdu.Sprawny);
        Mockito.when(repozytoriumRozkladowMock.wyszukaj("rozklad1")).thenReturn(rozklad);
        Mockito.when(repozytoriumSprawnychPojazdowMock.wyszukaj("pojazd1")).thenReturn(pojazd);
        Mockito.when(repozytoriumNiesprawnychPojazdowMock.wyszukaj("pojazd1")).thenReturn(null);
        //WHEN
        MockSUT.dodajPojazdDoRozkladu(new String[]{"pojazd1", "rozklad1"});
        //THEN
        Mockito.verify(rozklad, Mockito.atLeastOnce()).setPojazd(pojazd);
    }

    @Test
    void usunPojazdZRozkladu() {
        //GIVEN
        Facade MockSUT = new Facade(
                repozytoriumPrzystankowMock,
                repozytoriumTrasMock,
                repozytoriumRozkladowMock,
                repozytoriumSprawnychPojazdowMock,
                repozytoriumNiesprawnychPojazdowMock
        );
        Rozklad rozklad = Mockito.mock(Rozklad.class);
        Mockito.when(repozytoriumRozkladowMock.wyszukaj("rozklad1")).thenReturn(rozklad);
        //WHEN
        MockSUT.usunPojazdZRozkladu(new String[]{"rozklad1"});
        //THEN
        Mockito.verify(rozklad, Mockito.atLeastOnce()).setPojazd(null);
    }

    @Test
    void zglosAwarieNiesprawnegoPojazdu() throws ParseException {
        //GIVEN
        Facade MockSUT = new Facade(
                repozytoriumPrzystankowMock,
                repozytoriumTrasMock,
                repozytoriumRozkladowMock,
                repozytoriumSprawnychPojazdowMock,
                repozytoriumNiesprawnychPojazdowMock
        );
        Pojazd pojazd = Mockito.mock(Pojazd.class);
        Mockito.when(repozytoriumSprawnychPojazdowMock.wyszukaj("pojazd1")).thenReturn(null);
        Mockito.when(repozytoriumNiesprawnychPojazdowMock.wyszukaj("pojazd1")).thenReturn(pojazd);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date data = formatter.parse("20-JUL-2020");
        //WHEN
        MockSUT.zglosAwarie(new String[]{"pojazd1", "Niesprawny", "20-JUL-2020", "opisAwarii"});
        //THEN
        Mockito.verify(pojazd, Mockito.atLeastOnce()).setStatus(Pojazd.StatusPojazdu.Niesprawny);
        Mockito.verify(pojazd, Mockito.atLeastOnce()).addHistoryEntry(data, "opisAwarii");
    }

    @Test
    void zglosNaprawe() throws ParseException {
        //GIVEN
        Facade MockSUT = new Facade(
                repozytoriumPrzystankowMock,
                repozytoriumTrasMock,
                repozytoriumRozkladowMock,
                repozytoriumSprawnychPojazdowMock,
                repozytoriumNiesprawnychPojazdowMock
        );
        Pojazd pojazd = Mockito.mock(Pojazd.class);
        Mockito.when(repozytoriumSprawnychPojazdowMock.wyszukaj("pojazd1")).thenReturn(null);
        Mockito.when(repozytoriumNiesprawnychPojazdowMock.wyszukaj("pojazd1")).thenReturn(pojazd);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date data = formatter.parse("21-JUL-2020");
        //WHEN
        MockSUT.zglosNaprawe(new String[]{"pojazd1", "Sprawny", "21-JUL-2020", "opisNaprawy"});
        //THEN
        Mockito.verify(pojazd, Mockito.atLeastOnce()).setStatus(Pojazd.StatusPojazdu.Sprawny);
        Mockito.verify(pojazd, Mockito.atLeastOnce()).addHistoryEntry(data, "opisNaprawy");
        Mockito.verify(repozytoriumSprawnychPojazdowMock, Mockito.atLeastOnce()).dodaj(pojazd);
        Mockito.verify(repozytoriumNiesprawnychPojazdowMock, Mockito.atLeastOnce()).usun(pojazd);
    }
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        throw throwable;
    }
}