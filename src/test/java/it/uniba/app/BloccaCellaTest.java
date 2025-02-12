package it.uniba.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;


class BloccaCellaTest {

    private BloccaCella bloccaCella;

    @BeforeEach
    void setUp() {
        bloccaCella = new BloccaCella();
    }

    @AfterEach
    void tearDown() {
        bloccaCella.ripristinaTavolo();  // Ensure table is reset after each test
    }

    @Test
    void bloccaCellaCaselleDiPartenzaG1() {
        bloccaCella.bloccaCella("A1");
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(),
                     "La cella non può essere blocca perché è una casella di partenza");
    }

    @Test
    void bloccaCelleCasellaDiPartenzaG2() {
        bloccaCella.bloccaCella("A7");
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(), "la cella non può essere bloccata"
        + " perché è una casella di partenza");
    }

    @Test
    void bloccaCellaCasellaAdiacentePartenzaG1() {
        bloccaCella.bloccaCella("B1");
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(), "la cella non può essere bloccata"
        + " perché adiacente alla casella di partenza");

    }
    @Test
    void bloccaCellaCasellaDistanza2PartenzaG2() {
        bloccaCella.bloccaCella("C3");
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(), "la cella non può essere bloccata"
        + " perché è a distanza 2 da una casella di partenza e non può essere bloccata");
    }

    @Test
    void bloccaCellaInputNonValidoCaratteri() {
        bloccaCella.bloccaCella("XYZ");
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(), "L'input inserito non è una coordinata");
    }
    @Test
    void bloccaCelleInputNonValidoCellaFuoriTavolo() {
        bloccaCella.bloccaCella("H3");
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(), "L'input inserito è una cella fuori al tavolo");
    }

    @Test
    void bloccaCellaCelleValideG1() {
        bloccaCella.bloccaCella("D4");
        assertEquals(1, bloccaCella.getListaCelleBloccate().size(), "La cella D4 dovrebbe essere bloccata");
    }

    @Test
    void bloccaCelleCellaValidaG2() {
        bloccaCella.bloccaCella("E5");
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(),
             "La lista delle celle bloccate dovrebbe essere vuota dopo aver bloccato una casella di partenza");
    }

    @Test
    void bloccaCellaCelleDuplicate() {
        bloccaCella.bloccaCella("D4");
        bloccaCella.bloccaCella("D4");
        assertEquals(1, bloccaCella.getListaCelleBloccate().size(), "La cella D4 non dovrebbe essere"
        + " aggiunta più di una volta");
    }

    @Test
    void testGetListaCelleBloccate() {
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(), "La lista delle celle bloccate"
        + " dovrebbe essere inizialmente vuota");

    }

    @Test
    void ripristinaTavolo() {
        bloccaCella.bloccaCella("D4");
        bloccaCella.bloccaCella("E5");

        bloccaCella.ripristinaTavolo();
        assertEquals(0, bloccaCella.getListaCelleBloccate().size(), "La lista delle celle bloccate"
         + " dovrebbe essere vuota dopo il ripristino");
    }
}
