package it.uniba.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * "Control" Classe che gestisce il blocco e lo sblocco delle celle sul tavolo di gioco di Ataxx.
 * Responsabilità: gestisce lo stato del tavolo di gioco, blocca le celle in base alle richieste
 * dei giocatori memorizzandole. Lo sblocco delle celle avviene quando il giocatore termina o abbandona la partita.
 */
public class BloccaCella {

    /**
     * La costante DIMENSIONE serve per impostare la grandezza del tavoliere, impostata ad 8 per includere
     * anche la riga in cui visualizziamo la denominazione delle colonne
     * e la colonna in cui visualizziamo la denominazione delle righe.
     */
    private static final int DIMENSIONE = 8;

    /**
     * Matrice che rappresenta il tavolo di gioco.
     */
    private static char[][] tavoliere = new char[DIMENSIONE][DIMENSIONE];

    /**
     * Contiene il simbolo della cella bloccata.
     */
    private static final char BLOCCATO = '#';

    /**
     * La costante MAX_BLOCCABILE serve per impostare il massimo delle celle bloccabili sul tavoliere.
     */
    private static final int MAX_BLOCCABILE = 9;

    private final Tavolo tavolo;

    /**
     * Insieme che contiene le celle che non possono essere bloccate.
     */
    private static Set<String> celleNonBloccabili = new HashSet<>();

    /**
     * Lista di array per memorizzare le coordinate delle celle bloccate.
     */
    private static List<int[]> celleBloccate = new ArrayList<>();


    /**
     * Costruttore della classe BloccaCella.
     */
    public BloccaCella() {
        this.tavolo = new Tavolo();
        inizializzaCaselleDiPartenza();
    }

    /**
     * Inizializza la lista delle caselle di partenza.
     */
    private void inizializzaCaselleDiPartenza() {
        // Aggiungi le coordinate delle caselle di partenza alla lista
        celleNonBloccabili.add("A1");
        celleNonBloccabili.add("G1");
        celleNonBloccabili.add("A7");
        celleNonBloccabili.add("G7");
        // Aggiungi altre caselle di partenza se necessario
    }

    /**
     * Blocca una cella specificata rendendola non accessibile.
     *
     * @param coordinata la coordinata della cella da bloccare (es. "A1").
     */
    public void bloccaCella(final String coordinata) {
        // Inizializza il tavoliere con le celle bloccate
        tavolo.inizializzaTavolo(tavoliere, celleBloccate);
        // Controllo: verifica se il numero massimo di celle bloccate è stato raggiunto
        if (celleBloccate.size() >= MAX_BLOCCABILE) {
            System.out.println("Numero massimo di celle bloccate raggiunto (9).");
            return;
        }
        // Controllo: validazione dell'input
        if (coordinata.length() != 2 || !Character.isLetter(coordinata.charAt(0))
                || !Character.isDigit(coordinata.charAt(1))) {
            System.out.println("Input non valido. Utilizzo corretto: /blocca <coordinata> (es. /blocca A1)");
            return;
        }
        // Conversione della coordinata in indici di matrice
        int colonnaDaBloccare = Character.toUpperCase(coordinata.charAt(0)) - 'A' + 1;
        int rigaDaBloccare = Character.getNumericValue(coordinata.charAt(1));
        String cellaStr = rigaDaBloccare + "," + colonnaDaBloccare;
        // Controllo: verifica se la cella può essere bloccata
        if (celleNonBloccabili.contains(cellaStr)) {
            System.out.println("La cella specificata non può essere bloccata. Riprova.");
            return;
        }

        // Controllo: verifica se la cella è una casella di partenza o adiacente/distanza 2 da una casella di partenza
        for (String casellaPartenza : celleNonBloccabili) {
            int colonnaPartenza = Character.toUpperCase(casellaPartenza.charAt(0)) - 'A' + 1;
            int rigaPartenza = Character.getNumericValue(casellaPartenza.charAt(1));

            // Controllo se è una casella di partenza
            if (rigaDaBloccare == rigaPartenza && colonnaDaBloccare == colonnaPartenza) {
                System.out.println("La cella specificata \212 una casella di partenza e non può essere bloccata.\n"
                + "Riprova.");
                return;
            }

            // Controllo se è adiacente (distanza 1)
            if (Math.abs(rigaDaBloccare - rigaPartenza) <= 1 && Math.abs(colonnaDaBloccare - colonnaPartenza) <= 1) {
                System.out.println("La cella inserita è adiacente a una casella di partenza,"
                 + " non può essere bloccata.\n Riprova.");
                return;
            }

            // Controllo se è a distanza 2
            if (Math.abs(rigaDaBloccare - rigaPartenza) <= 2 && Math.abs(colonnaDaBloccare - colonnaPartenza) <= 2) {
                System.out.println("La cella inserita è a distanza 2 da una casella"
                + " di partenza e non può essere bloccata.\n Riprova.");
                return;
            }
        }
        // Controllo: verifica se la cella è entro i limiti e non già occupata
        if (rigaDaBloccare > 0 && rigaDaBloccare < DIMENSIONE && colonnaDaBloccare > 0
                && colonnaDaBloccare < DIMENSIONE && tavoliere[rigaDaBloccare][colonnaDaBloccare] == '-') {
            // Blocca la cella
            tavoliere[rigaDaBloccare][colonnaDaBloccare] = BLOCCATO;
            memorizzaCellaBloccata(rigaDaBloccare, colonnaDaBloccare);
            System.out.println("Cella " + coordinata + " bloccata con successo.");
        } else {
            System.out.println("La cella specificata è fuori dal tavolo o già occupata. Riprova.");
        }
    }

    /**
     * Memorizza le coordinate della cella bloccata in un array di liste.
     *
     * @param riga    La riga della cella bloccata.
     * @param colonna La colonna della cella bloccata.
     */
    private void memorizzaCellaBloccata(final int riga, final int colonna) {
        celleBloccate.add(new int[]{riga, colonna});
    }

    /**
     * Ripristina il tavolo di gioco allo stato iniziale, sbloccando tutte le celle
     * bloccate.
     */
    public void ripristinaTavolo() {
        System.out.println("Ripristino il tavolo di gioco allo stato iniziale.");
        celleBloccate.clear(); // Sblocca tutte le celle bloccate
        System.out.println("Tavolo ripristinato. Celle bloccate: " + celleBloccate.size());
    }

    /**
     * Restituisce la lista delle celle bloccate sul tavolo di gioco.
     *
     * @return La lista delle celle bloccate.
     */
    public List<int[]> getListaCelleBloccate() {
        return new ArrayList<>(celleBloccate);
    }
}
