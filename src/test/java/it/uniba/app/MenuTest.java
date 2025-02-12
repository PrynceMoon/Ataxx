package it.uniba.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testMenuGiocoCommandoBloccaConCoordinataAdiacente() {
        Menu menu = new Menu();
        menu.menuGioco("/blocca A2", false);

        String expectedOutput = "La cella inserita è adiacente a una casella di partenza, "
                 + "non può essere bloccata.\n Riprova.";
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8).trim(), "Output mismatch");
    }

    @Test
    void testMenuGiocoCommandoBloccaConCoordinataDiPartenza() {
        Menu menu = new Menu();
        menu.menuGioco("/blocca A1", false);

        String expectedOutput = "La cella specificata \212 una casella di partenza e non può essere bloccata.\n"
                + "Riprova.";
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8).trim(), "Output mismatch");
    }

    @Test
    void testMenuGiocoCommandoBloccaConCoordinataValida() {
        Menu menu = new Menu();
        menu.menuGioco("/blocca A4", false);

        String expectedOutput = "Cella A4 bloccata con successo.";
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8).trim(), "Output mismatch");
    }

    @Test
    void testMenuGiocoCommandoTavoliereSenzaPartita() {
        Menu menu = new Menu();
        menu.menuGioco("/tavoliere", false);

        String expectedOutput = "Partita non trovata.\n Esegui il comando '/gioca' e digita il comando";
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8).trim(), "Output mismatch");
    }

    @Test
    void testMenuGiocoCommandoQualiMosseSenzaPartita() {
        Menu menu = new Menu();
        menu.menuGioco("/qualimosse", false);

        String expectedOutput = "Partita non trovata.\n Esegui il comando '/gioca' e digita il comando";
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8).trim(), "Output mismatch");
    }

    @Test
    void testMenuGiocoCommandoAbbandonaSenzaPartita() {
        Menu menu = new Menu();
        menu.menuGioco("/abbandona", false);

        String expectedOutput = "Partita non trovata.\n Esegui il comando '/gioca' e digita il comando";
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8).trim(), "Output mismatch");
    }
}
