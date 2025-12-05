package domaca_naloga2;

import org.easymock.TestSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeznamiUVTest {

    SeznamiUV uv = new SeznamiUV();


    @BeforeEach
    public void setUp() {
        uv = new SeznamiUV();
        uv.addImpl("23", new Drevo23<>(new DiplomatPrimerjajPoImenu()), new Drevo23<>(new DiplomatPrimerjajPoVpSt()));
        uv.processInput("use 23");
        uv.processInput("izprazni");
    }

    @Test
    public void brezUkaz() {
        assertEquals("Vnesite ukaz", uv.processInput(""));
    }

    @Test
    public void useOk() {
        System.out.println("useOk");
        assertEquals("V redu", uv.processInput("uporabi 23"));
    }

    @Test
    public void useWrongDataType() {
        System.out.println("useWrongDataType");
        assertEquals("Prosim, navedite pravilen tip podatkovne strukture {23}", uv.processInput("uporabi pvc"));
    }

    @Test
    public void useNoDataType() {
        System.out.println("useNoDataType");
        assertEquals("Prosim, navedite podatkovno strukturo {23}", uv.processInput("uporabi"));
    }


    @Test
    public void testAdd() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
    }

    @Test
    public void testAddNapSt() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230455\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Napačni podatki", uv.processInput("dodaj"));
        simulatedUserInput =
                "22230455\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Napačni podatki", uv.processInput("dodaj"));
    }

    @Test
    public void testAddNapPr() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "TK\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Napačni podatki", uv.processInput("dodaj"));
    }

    @Test
    public void testAddNapLeto() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2023\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Napačni podatki", uv.processInput("dodaj"));
    }

    @Test
    public void testAddZeObst() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Diplomant že obstaja", uv.processInput("dodaj"));
    }

    @Test
    public void testOdstraniVpSt() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        assertEquals("V redu", uv.processInput("odstrani 63230453"));
    }

    @Test
    public void testOdstraniNapSt() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("Napačni podatki", uv.processInput("odstrani 63230455"));
        assertEquals("Napačni podatki", uv.processInput("odstrani 65230455"));
    }

    @Test
    public void testOdstraniVpStNeObst() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        assertEquals("Diplomant ne obstaja", uv.processInput("odstrani 63230442"));
    }

    @Test
    public void testOdstraniIme() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        simulatedUserInput = "Tobey\n" + "Maguire\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("odstrani"));
    }

    @Test
    public void testOdstraniImeNeObst() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        simulatedUserInput = "Tom\n" + "Holland\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Diplomant ne obstaja", uv.processInput("odstrani"));
    }

    @Test
    public void testOdstraniEmpty() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("Diplomant ne obstaja", uv.processInput("odstrani 63230453"));
    }

    @Test
    public void testPoisciVp() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        assertEquals("\t63230453 | Maguire, Tobey | VSS RI | 2026 | Naslov diplome", uv.processInput("poisci 63230453"));
    }

    @Test
    public void testPoisciVpNeObst() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        assertEquals("Diplomant ne obstaja", uv.processInput("poisci 63230442"));
    }

    @Test
    public void testPoisciIme() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));

        simulatedUserInput = "Tobey\n" + "Maguire\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("\t63230453 | Maguire, Tobey | VSS RI | 2026 | Naslov diplome", uv.processInput("poisci"));
    }

    @Test
    public void testPoisciImeNeObst() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));

        simulatedUserInput = "Tom\n" + "Holland\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Diplomant ne obstaja", uv.processInput("poisci"));
    }

    @Test
    public void testPoisciNap() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        assertEquals("Napačni podatki", uv.processInput("poisci 63230455"));
    }

    @Test
    public void testPrestej() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("0", uv.processInput("prestej"));
    }

    @Test
    public void testIsprazniDa() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput = "d";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("izprazni"));
    }

    @Test
    public void testIsprazniNe() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput = "n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("Ukaz je bil preklican", uv.processInput("izprazni"));
    }

    @Test
    public void testIzpisi() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        assertEquals("V redu", uv.processInput("izpisi"));
    }

    @Test
    public void tesNapUkaz() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("Napačen ukaz", uv.processInput("spiderman"));
    }

    @Test
    public void testIzhod() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("Nasvidenje", uv.processInput("izhod"));
    }

    @Test
    public void testShraniPovrni() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        String simulatedUserInput =
                "63230453\n" +
                        "Tobey\n" +
                        "Maguire\n" +
                        "VSS RI\n" +
                        "2026\n" +
                        "Naslov diplome\n";

        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("dodaj"));
        assertEquals("V redu", uv.processInput("shrani data.bin"));
        simulatedUserInput = "d";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        assertEquals("V redu", uv.processInput("izprazni"));
        assertEquals("V redu", uv.processInput("povrni data.bin"));
        assertEquals("V redu", uv.processInput("izpisi"));
    }

    @Test
    public void testShPovBrezExt() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("V redu", uv.processInput("shrani data"));
        assertEquals("I/O napaka null", uv.processInput("povrni data"));

    }

    @Test
    public void testShraniBrezImeDat() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("Prosim, navedite ime datoteke", uv.processInput("shrani"));
    }

    @Test
    public void testPovrniBrezImeDat() {
        assertEquals("V redu", uv.processInput("uporabi 23"));
        assertEquals("Prosim, navedite ime datoteke", uv.processInput("povrni"));
    }
}