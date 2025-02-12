package it.uniba.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Test per la classe Giocatore.
 */
class GiocatoreTest {

    /**
     * Testa il metodo chiediNomeGiocatore della classe Giocatore.
     */
    @Test
    void testChiediNomeGiocatore() {
        // Imposta l'input simulato
        String input = "Menny\n"; // Simula l'input "Menny" seguito da un invio
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        // Crea un'istanza della classe Giocatore
        Giocatore giocatore = new Giocatore();

        // Esegui il metodo chiediNomeGiocatore, che ora legge da System.in
        giocatore.chiediNomeGiocatore();

        // Contenuto atteso senza il carattere di nuova riga alla fine
        String expectedInput = "Menny";

        // Verifica l'input effettivo, rimuovendo eventuali spazi o caratteri di nuova riga
        String actualInput = giocatore.getNomeGiocatore().trim();

        // Verifica se l'input è corretto
        assertEquals(expectedInput, actualInput, "Il nome del giocatore dovrebbe essere 'Menny'");
    }

    /**
     * Testa il metodo abbandonaPartita della classe Giocatore con conferma "no".
     */
    @Test
    void testAbbandonaPartitaConfermaNo() {
        // Imposta l'input simulato
        String input = "no\n"; // Simula l'input "no" seguito da un invio
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        // Crea un'istanza della classe Partita
        Partita partita = new Partita();

        // Crea un giocatore corrente
        Giocatore giocatoreCorrente = new Giocatore();

        // Esegui il metodo abbandonaPartita
        Giocatore.abbandonaPartita(partita, new Scanner(in, StandardCharsets.UTF_8), giocatoreCorrente);

        // Verifica se la partita non è stata terminata
        assertFalse(partita.isPartitaInCorso(), "La partita non dovrebbe essere terminata");
    }

    /**
     * Testa il metodo abbandonaPartita della classe Giocatore con input non valido seguito da conferma.
     */
    @Test
    void testAbbandonaPartitaInputNonValido() {
        // Imposta l'input simulato
        String input = "invalido\nsi\n"; // Simula l'input "invalido", seguito da "si" (conferma) seguito da un invio
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        // Crea un'istanza della classe Partita
        Partita partita = new Partita();

        // Crea un giocatore corrente
        Giocatore giocatoreCorrente = new Giocatore();

        // Esegui il metodo abbandonaPartita
        Giocatore.abbandonaPartita(partita, new Scanner(in, StandardCharsets.UTF_8), giocatoreCorrente);

        // Verifica se la partita non è stata terminata
        assertFalse(partita.isPartitaInCorso(), "La partita non dovrebbe essere terminata");
    }
}
