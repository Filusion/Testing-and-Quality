package domaca_naloga2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Seznam<Tip> {
    // Dodajanje elementa v podatkovno strukturo
    void add(Tip e);
    // Odstranjevanje (in vračanje) prvega elementa iz pod. struk.
    Tip removeFirst();
    // Odstranjevanje (in vračanje) poljubnega elementa iz pod. struk.
    Tip remove(Tip e);
    // Vračanje prvega elementa iz pod. struk.
    Tip getFirst();
    // Ali je element prisoten v strukturi
    boolean exists(Tip e);
    // Število elementov v podatkovni strukturi
    int size();
    // Globina podatkovne strukture
    int depth();
    // Ali je podakovna struktura prazna
    boolean isEmpty();
    // Izpis podatkovne strukture 
    void print();
    // Shranjevanje vsebine v datoteko
    void save(OutputStream outputStream) throws IOException;
    // Restavriranje vsebine iz datoteke
    void restore(InputStream inputStream) throws IOException,
            ClassNotFoundException;
    Tip getElement(Tip e);
}
