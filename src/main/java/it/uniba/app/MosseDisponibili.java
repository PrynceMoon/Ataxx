package it.uniba.app;

/*
 * Classe contenente i comandi eseguibili in gioco.
 */
/**
 * "Boundary"
 * Classe che gestisce i comandi del gioco Ataxx. Responsabilità:
 * mostrare il tavoliere, visualizzare le mosse disponibili, e gestire
 * l'abbandono della partita.
 */
public final class MosseDisponibili {

    /*
     * costante per il controllo delle mosse evidando i numeri magici
     */
    private static final int CONTROLLOMOSSEB = -2;

    /*
     * costante per i cicli evidando i numeri magici
     */
    private static final int DIMENSIONE = 8;

    /**
     * Codice ANSI per resettare il colore dello sfondo.
     */
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Codice ANSI per impostare il colore dello sfondo a giallo per le celle che permettono la mossa di tipo A.
     */
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    /**
     * Codice ANSI per impostare il colore del testo a arancione per le celle che permettono la mossa di tipo B.
     */
    private static final String ANSI_ORANGE_BACKGROUND = "\u001B[48;5;214m";

    /**
     * Codice ANSI per impostare il colore dello sfondo a rosa per le celle che permettono le mosse di tipo A e B.
     */
    private static final String ANSI_PINK_BACKGROUND = "\u001B[45m";

    /**
     * Codice ANSI per impostare il colore dello sfondo a grigio per la cella bloccata.
     */
    private static final String ANSI_GRAY_BACKGROUND = "\u001b[100m"; // Grigio chiaro

    /*
     * Indicatore per la cella bloccata
     */
    private static final char BLOCCATO = '#';

    /**
     * Costruttore predefinito della classe Comandi.
     * Inizializza la classe senza ulteriori configurazioni.
     */
    private MosseDisponibili() {
        // Costruttore predefinito
    }

    /**
     * Visualizza le mosse disponibili per il giocatore corrente.
     *
     * @param tavolo            Il tavolo di gioco attuale.
     * @param giocatoreCorrente Il simbolo del giocatore corrente.
     */
    public static void qualimosse(final char[][] tavolo, final char giocatoreCorrente) {
        // Creiamo una copia del tavolo per non modificare l'originale
        char[][] tavoloConMosse = new char[DIMENSIONE][DIMENSIONE];
        for (int i = 0; i < DIMENSIONE; i++) {
            System.arraycopy(tavolo[i], 0, tavoloConMosse[i], 0, tavolo[i].length);
        }

        System.out.println("Mosse disponibili per il giocatore " + giocatoreCorrente + ":");
        System.out.println("La lettera 'A' permette di clonare la pedina");
        System.out.println("La lettera 'B' permette di far saltare la pedina in quella cella");
        System.out.println("La lettera 'C' permette entrambe le mosse");

        for (int i = 1; i < DIMENSIONE; i++) {
            for (int j = 1; j < DIMENSIONE; j++) {
                if (tavolo[i][j] == giocatoreCorrente) {

                    // Controllo mosse di tipo A (adiacenti)
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            if (di == 0 && dj == 0) {
                                continue;
                            }
                            int newRow = i + di;
                            int newCol = j + dj;
                            if (mossaValida(newRow, newCol, tavolo)) {
                                if (tavoloConMosse[newRow][newCol] == 'B') {
                                    tavoloConMosse[newRow][newCol] = 'C';
                                } else {
                                    tavoloConMosse[newRow][newCol] = 'A';
                                }
                            }
                        }
                    }

                    // Controllo mosse di tipo B (salto)
                    for (int di = CONTROLLOMOSSEB; di <= 2; di++) {
                        for (int dj = CONTROLLOMOSSEB; dj <= 2; dj++) {
                            if (di == 0 && dj == 0) {
                                continue;
                            }
                            if (Math.abs(di) == 2 || Math.abs(dj) == 2) {
                                int newRow = i + di;
                                int newCol = j + dj;
                                if (mossaValida(newRow, newCol, tavolo)) {
                                    if (tavoloConMosse[newRow][newCol] == 'A') {
                                        tavoloConMosse[newRow][newCol] = 'C';
                                    } else {
                                        tavoloConMosse[newRow][newCol] = 'B';
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Stampa il tavolo con le mosse segnate
        stampaTavoloConMosse(tavoloConMosse);
    }

    /**
     * Verifica se una mossa è valida.
     *
     * @param riga    La riga della posizione da verificare.
     * @param colonna La colonna della posizione da verificare.
     * @param tavolo  Il tavolo di gioco attuale.
     * @return true se la mossa è valida, false altrimenti.
     */
    private static boolean mossaValida(final int riga, final int colonna, final char[][] tavolo) {
        return riga >= 1 && riga < tavolo.length && colonna >= 1 && colonna < tavolo[riga].length
                && tavolo[riga][colonna] == '-';
    }

    /**
     * Stampa il tavolo con le mosse disponibili evidenziate.
     *
     * @param tavolo Il tavolo di gioco con le mosse disponibili evidenziate.
     */
    private static void stampaTavoloConMosse(final char[][] tavolo) {
        System.out.println("╭-------------------------------------------------------╮");
        for (int i = 0; i < DIMENSIONE; i++) {
            System.out.print("| ");
            for (int j = 0; j < DIMENSIONE; j++) {
                char cell = tavolo[i][j];
                if (i == 0 || j == 0) {
                    // Non colorare le lettere delle colonne e i numeri delle righe
                    System.out.printf("%-4c | ", cell);
                } else {
                    switch (cell) {
                        case 'A':
                            System.out.printf(ANSI_YELLOW_BACKGROUND + "%-4c" + ANSI_RESET + " | ", cell);
                            break;
                        case 'B':
                            System.out.printf(ANSI_ORANGE_BACKGROUND + "%-4c" + ANSI_RESET + " | ", cell);
                            break;
                        case 'C':
                            System.out.printf(ANSI_PINK_BACKGROUND + "%-4c" + ANSI_RESET + " | ", cell);
                            break;
                        case '#':
                            System.out.print(ANSI_GRAY_BACKGROUND + " " + BLOCCATO + " " + ANSI_RESET + "  | ");
                            break;
                        default:
                            System.out.printf("%-4c | ", cell);
                            break;
                    }
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
