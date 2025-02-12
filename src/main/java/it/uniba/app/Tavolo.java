package it.uniba.app;

import java.util.List;
import java.util.ArrayList;

/**
 * "Entity" Classe che rappresenta il tavolo di gioco di Ataxx. Responsabilità:
 * gestisce lo stato del tavolo, aggiorna le posizioni delle pedine e verifica
 * la validità delle mosse.
 */
public class Tavolo {

    private static final int POS1 = 1;
    private static final int POS2 = 7;
    private static final int DIMENSIONE = 8;
    private static final char BLOCCATO = '#';
    private static char[][] tavolo = new char[DIMENSIONE][DIMENSIONE];
    private static final String ANSI_GRAY_BACKGROUND = "\u001b[100m"; // Grigio chiaro
    private static final String ANSI_RESET = "\u001b[0m"; // Resetta i colori
    private static final char PEDINA_NERA = '\u26AB';
    private static final char PEDINA_BIANCA = '\u26AA';

    /**
     * Costruttore della classe Tavolo.
     */
    public Tavolo() {
        List<int[]> celleBloccate = new ArrayList<>();
        // Aggiungi celle bloccate se necessario
        inizializzaTavolo(tavolo, celleBloccate);
    }

    /**
     * Inizializza il tavolo di gioco con le posizioni iniziali delle pedine.
     *
     * @param tavoloIniziale Il tavolo di gioco da inizializzare.
     * @param celleBloccate le celle bloccate.
     */
    public void inizializzaTavolo(final char[][] tavoloIniziale, final List<int[]> celleBloccate) {
        for (int i = 0; i < DIMENSIONE; i++) {
            for (int j = 0; j < DIMENSIONE; j++) {
                if (i == 0 && j > 0) {
                    tavoloIniziale[i][j] = (char) ('A' + j - 1); // Lettere sopra il tavolo
                } else if (j == 0 && i > 0) {
                    tavoloIniziale[i][j] = (char) ('0' + i); // Numeri a sinistra del tavolo
                } else if (i == 0 || j == 0) {
                    tavoloIniziale[i][j] = ' '; // Spazi vuoti per l'angolo
                } else {
                    tavoloIniziale[i][j] = '-';
                }
            }
        }

        for (int[] cella : celleBloccate) {
            int riga = cella[0];
            int colonna = cella[1];
            tavoloIniziale[riga][colonna] = BLOCCATO;
        }

        tavoloIniziale[POS1][POS1] = 'X'; // Pedina X in A1
        tavoloIniziale[POS2][POS2] = 'X'; // Pedina X in G7
        tavoloIniziale[POS1][POS2] = 'O'; // Pedina O in A7
        tavoloIniziale[POS2][POS1] = 'O'; // Pedina O in G1
    }

    /**
     * Stampa il tavolo di gioco con le pedine nelle posizioni attuali, inclusi i
     * simboli delle celle bloccate.
     *
     * @param tavoloDaStampare Il tavolo di gioco da stampare.
     */
    public void stampaTavolo(final char[][] tavoloDaStampare) {
        System.out.println("╭-------------------------------------------------------╮");
        for (int i = 0; i < DIMENSIONE; i++) {
            System.out.print("| ");
            for (int j = 0; j < DIMENSIONE; j++) {
                char cella = tavoloDaStampare[i][j];
                if (cella == BLOCCATO) {
                    System.out.print(ANSI_GRAY_BACKGROUND + " " + BLOCCATO + " " + ANSI_RESET + "  | ");
                } else {
                    System.out.printf("%-4c | ", cella);
                }
            }
            System.out.println();
            if (i < DIMENSIONE - 1) {
                System.out.println("|-------------------------------------------------------|");
            }
        }
        System.out.println("╰-------------------------------------------------------╯");
    }

    /**
     * Stampa una rappresentazione vuota del tavolo di gioco.
     */
    public void stampaVuoto() {
        System.out.println("╭-------------------------------------------------------╮");
        for (int i = 0; i < DIMENSIONE; i++) {
            System.out.print("| ");
            for (int j = 0; j < DIMENSIONE; j++) {
                if (i == 0 && j > 0) {
                    System.out.printf("%-4c | ", (char) ('A' + j - 1)); // Lettere sopra il tavolo
                } else if (j == 0 && i > 0) {
                    System.out.printf("%-4c | ", (char) ('0' + i)); // Numeri a sinistra del tavolo
                } else {
                    System.out.print("-    | "); // Celle vuote del tavolo
                }
            }
            System.out.println();
            if (i < DIMENSIONE - 1) {
                System.out.println("|-------------------------------------------------------|");
            }
        }
        System.out.println("╰-------------------------------------------------------╯");
    }

    /**
     * Stampa il tavolo di gioco con le pedine nelle posizioni attuali, inclusi i simboli delle celle bloccate.
     */
    public void tavoliere(final char[][] tavoloDaStampare) {
            System.out.println("╭-------------------------------------------------------╮");
        for (int i = 0; i < DIMENSIONE; i++) {
            System.out.print("| ");
            for (int j = 0; j < DIMENSIONE; j++) {
                char cella = tavoloDaStampare[i][j];
                // Visualizzazione delle pedine in formato Unicode
                if (cella == 'X') {
                    System.out.print(" " + PEDINA_NERA + "  | ");
                } else if (cella == 'O') {
                    System.out.print(" " + PEDINA_BIANCA + "  | ");
                } else if (cella == BLOCCATO) {
                    System.out.print(ANSI_GRAY_BACKGROUND + " " + BLOCCATO + " " + ANSI_RESET + "  | ");
                } else {
                    System.out.printf("%-4c | ", cella);
                }
            }
            System.out.println();
            if (i < DIMENSIONE - 1) {
                System.out.println("|-------------------------------------------------------|");
            }
        }
        System.out.println("╰-------------------------------------------------------╯");
    }
}
