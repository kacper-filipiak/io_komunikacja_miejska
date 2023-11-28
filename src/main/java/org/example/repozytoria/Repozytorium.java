package org.example.repozytoria;

public interface Repozytorium<T> {
    T wyszukaj(String nazwa);
    T dodaj(T element);
}
