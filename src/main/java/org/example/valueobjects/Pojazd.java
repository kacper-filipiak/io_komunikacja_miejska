package org.example.valueobjects;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pojazd {
   private final String identyfikator;
   private StatusPojazdu status;
   private final Map<Date, String> historia;

    public Pojazd(String identyfikator, StatusPojazdu statusPojazdu) {
        this.identyfikator = identyfikator;
        this.status= statusPojazdu;
        this.historia = new HashMap<>();
    }

    public Map<Date, String> getHistoria() {
        return historia;
    }

    public StatusPojazdu getStatus() {
        return status;
    }

    public String getIdentyfikator() {
        return identyfikator;
    }

    public void setStatus(StatusPojazdu status) {
        this.status = status;
    }

    public void addHistoryEntry(Date time, String description) {
       historia.put(time, description);
    }
    public enum StatusPojazdu {
       Sprawny,
       Niesprawny
   }
}
