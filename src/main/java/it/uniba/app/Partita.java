package it.uniba.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * "Control" Classe che gestisce la logica della partita nel gioco Ataxx.
 * Responsabilità: inizializza il tavolo, gestisce il ciclo di gioco, controlla
 * la validità delle mosse e verifica le condizioni di fine partita.
 */
public class Partita {

    private static final int CP1 = 1;
    private static final int CP2 = 7;
    private static final int DIMENSIONE = 8;
    private static final int CONTROLLO_TURNO1 = 2;
    private static final int CONTROLLO_TURNO2 = -2;
    private char[][] tavolo;
    private Giocatore giocatore1;
    private Giocatore giocatore2;
    private boolean partitaInCorso;
    private boolean risultatoMostrato;
    private Scanner scanner;
    private List<String> storiaMosse;

    /**
     * Costruttore della classe Partita. Inizializza il tavolo, lo scanner e il
     * giocatore.
     */
    public Partita() {
        tavolo = new char[DIMENSIONE][DIMENSIONE];
        scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        giocatore1 = new Giocatore('X');
        giocatore2 = new Giocatore('O');
        storiaMosse = new ArrayList<>();
    }

    /**
     * Restituisce una copia del tavolo di gioco.
     *
     * @return Una matrice bidimensionale di caratteri che rappresenta il tavolo di
     *         gioco.
     */
    public char[][] getTavolo() {
        char[][] copiaTavolo = new char[DIMENSIONE][];
        for (int i = 0; i < DIMENSIONE; i++) {
            copiaTavolo[i] = tavolo[i].clone();
        }
        return copiaTavolo;
    }

    /**
     * Verifica se la partita è in corso.
     *
     * @return true se la partita è in corso, false altrimenti.
     */
    public boolean isPartitaInCorso() {
        return partitaInCorso;
    }

    /**
     * Restituisce i giocatori della partita.
     *
     * @return Un array contenente i due giocatori.
     */
    public Giocatore[] getGiocatori() {
        return new Giocatore[] {giocatore1, giocatore2};
    }

    /**
     * Imposta lo stato della partita.
     *
     * partitaInCorso true per impostare la partita in corso, false
     * altrimenti.
     */
    public void setPartitaInCorso(final boolean partitaInCorsoFlag) {
        this.partitaInCorso = partitaInCorsoFlag;
    }

    /**
     * Avvia una nuova partita di Ataxx. Inizializza il tavolo, chiede il nome
     * del giocatore e gestisce il ciclo di gioco.
     */
    public void iniziaPartita() {
        Tavolo tavoloLocale = new Tavolo();
        BloccaCella bloccaCella = new BloccaCella();
        tavoloLocale.inizializzaTavolo(tavolo, bloccaCella.getListaCelleBloccate());
        giocatore1.chiediNomeGiocatore();
        giocatore2.chiediNomeGiocatore();
        tavoloLocale.stampaTavolo(tavolo);
        partitaInCorso = true;
        risultatoMostrato = false;

        Giocatore giocatoreCorrente = giocatore1;
        while (partitaInCorso) {
            if (controllaTurno(giocatoreCorrente)) {
                giocatoreCorrente.turnoGiocatore(this);
                Giocatore perdente = controllaPedinePerse(); // Gestione del valore di ritorno
                if (perdente != null && !risultatoMostrato) {
                    mostraRisultato(perdente);
                    risultatoMostrato = true;
                    partitaInCorso = false; // Termina la partita
                } else if (controllaFinePartita() && !risultatoMostrato) {
                    mostraRisultato(null);
                    risultatoMostrato = true;
                    partitaInCorso = false; // Termina la partita
                }
            } else {
                System.out.println("Nessuna mossa disponibile per " + giocatoreCorrente.getNomeGiocatore());
            }
            giocatoreCorrente = (giocatoreCorrente == giocatore1) ? giocatore2 : giocatore1;
        }
    }

    /**
     * Gestisce l'inserimento di una pedina nel tavolo di gioco. Verifica la
     * validità della posizione e della mossa.
     *
     * @param utentecorrente Il giocatore corrente che sta effettuando la mossa.
     * @return Il giocatore successivo.
     */
    public Giocatore inserisciPedina(final Giocatore utentecorrente) {
    boolean posizioneValida = false;

    while (!posizioneValida) {
        System.out.println("Inserisci la posizione iniziale e la posizione di arrivo (es. A1 B2): ");
        String input = scanner.nextLine().trim().toUpperCase();
        String[] posizioni = input.split(" ");

        if (posizioni.length != 2) {
            System.out.println("Input non valido. Inserisci due posizioni (es. A1 B2).");
            continue;
        }

        String posizioneIniziale = posizioni[0];
        String posizioneArrivo = posizioni[1];

        if (!posizioneValida(posizioneIniziale) || !posizioneValida(posizioneArrivo)) {
            System.out.println("Posizioni non valide. Riprova.");
            continue;
        }

        int colonnaIniziale = posizioneIniziale.charAt(0) - 'A' + 1;
        int rigaIniziale = Integer.parseInt(posizioneIniziale.substring(1));
        int colonnaArrivo = posizioneArrivo.charAt(0) - 'A' + 1;
        int rigaArrivo = Integer.parseInt(posizioneArrivo.substring(1));

        if (tavolo[rigaIniziale][colonnaIniziale] != utentecorrente.getGiocatoreCorrente()) {
            System.out.println("La posizione iniziale non contiene una tua pedina. Riprova.");
            continue;
        }

        if (Math.abs(rigaArrivo - rigaIniziale) <= 1 && Math.abs(colonnaArrivo - colonnaIniziale) <= 1) {
            if (mossaValidaA(rigaIniziale, colonnaIniziale, rigaArrivo, colonnaArrivo, utentecorrente)) {
                if (tavolo[rigaArrivo][colonnaArrivo] == '-') {
                    tavolo[rigaArrivo][colonnaArrivo] = utentecorrente.getGiocatoreCorrente();
                    aggiornaPedineAvversarie(rigaArrivo, colonnaArrivo);
                    // Chiamata controllaPedinePerse rimossa poiché il valore di ritorno viene ignorato
                    posizioneValida = true;
                } else {
                    System.out.println("La posizione di arrivo non è valida. Riprova.");
                }
            } else {
                System.out.println("La mossa non è valida. Puoi copiare una pedina solo adiacente.");
            }
        } else if (mossaValidaB(rigaIniziale, colonnaIniziale, rigaArrivo, colonnaArrivo, utentecorrente)) {
            if (tavolo[rigaArrivo][colonnaArrivo] == '-') {
                tavolo[rigaIniziale][colonnaIniziale] = '-';
                tavolo[rigaArrivo][colonnaArrivo] = utentecorrente.getGiocatoreCorrente();
                aggiornaPedineAvversarie(rigaArrivo, colonnaArrivo);
                // Chiamata controllaPedinePerse rimossa poiché il valore di ritorno viene ignorato
                posizioneValida = true;
            } else {
                System.out.println("La posizione di arrivo non è valida. Riprova.");
            }
        } else {
            System.out.println("La mossa non è valida. Riprova.");
        }

        if (posizioneValida) {
            Tavolo nuovoTavolo = new Tavolo();
            nuovoTavolo.stampaTavolo(this.getTavolo());

            storiaMosse.add(storiaMosse.size() + 1 + ". " + posizioneIniziale + "-"
                    + posizioneArrivo + " (" + utentecorrente.getGiocatoreCorrente() + ")");
            if (utentecorrente.equals(giocatore1)) {
                return giocatore2;
            } else {
                return giocatore1;
            }
        }
    }
    return utentecorrente;
}

    /**
     * Verifica se una mossa è valida per il giocatore corrente secondo le regole di
     * tipo A.
     *
     * @param rigaIniziale    La riga della posizione iniziale.
     * @param colonnaIniziale La colonna della posizione iniziale.
     * @param rigaArrivo      La riga della posizione di arrivo.
     * @param colonnaArrivo   La colonna della posizione di arrivo.
     * @param giocatore       Il giocatore corrente.
     * @return true se la mossa è valida, false altrimenti.
     */
    private boolean mossaValidaA(final int rigaIniziale, final int colonnaIniziale,
            final int rigaArrivo, final int colonnaArrivo, final Giocatore giocatore) {
        int diffRiga = Math.abs(rigaArrivo - rigaIniziale);
        int diffColonna = Math.abs(colonnaArrivo - colonnaIniziale);
        return (diffRiga <= 1 && diffColonna <= 1) && tavolo[rigaArrivo][colonnaArrivo] == '-';
    }

    /**
     * Verifica se una mossa è valida per il giocatore corrente secondo le regole di
     * tipo B.
     *
     * @param rigaOrig    La riga della posizione iniziale.
     * @param colonnaOrig La colonna della posizione iniziale.
     * @param rigaDest    La riga della posizione di arrivo.
     * @param colonnaDest La colonna della posizione di arrivo.
     * @param giocatore   Il giocatore corrente.
     * @return true se la mossa è valida, false altrimenti.
     */
    private boolean mossaValidaB(final int rigaOrig, final int colonnaOrig,
            final int rigaDest, final int colonnaDest, final Giocatore giocatore) {
        int diffRiga = Math.abs(rigaDest - rigaOrig);
        int diffColonna = Math.abs(colonnaDest - colonnaOrig);
        return (diffRiga == 2 && diffColonna == 0) || (diffRiga == 0 && diffColonna == 2)
                || (diffRiga == 2 && diffColonna == 1) || (diffRiga == 1 && diffColonna == 2)
                || (diffRiga == 2 && diffColonna == 2);
    }

    /**
     * Verifica se una posizione è valida (all'interno dei limiti del tavolo di
     * gioco).
     *
     * @param posizione La posizione da verificare.
     * @return true se la posizione è valida, false altrimenti.
     */
    public boolean posizioneValida(final String posizione) {
        if (posizione.length() != 2 || !Character.isLetter(posizione.charAt(0))
                || !Character.isDigit(posizione.charAt(1))) {
            return false;
        }
        int colonna = posizione.charAt(0) - 'A' + 1;
        int riga = Integer.parseInt(posizione.substring(1));
        return riga >= CP1 && riga <= CP2 && colonna >= CP1 && colonna <= CP2;
    }

    /**
     * Aggiorna le pedine avversarie adiacenti dopo l'inserimento di una nuova
     * pedina.
     *
     * @param riga    La riga della posizione della nuova pedina.
     * @param colonna La colonna della posizione della nuova pedina.
     */
    public void aggiornaPedineAvversarie(final int riga, final int colonna) {
        char simboloGiocatore = tavolo[riga][colonna];
        char simboloAvversario = (simboloGiocatore == giocatore1.getGiocatoreCorrente())
                ? giocatore2.getGiocatoreCorrente()
                : giocatore1.getGiocatoreCorrente();
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                int nr = riga + di;
                int nc = colonna + dj;
                if (posizioneValida(nr, nc) && tavolo[nr][nc] == simboloAvversario) {
                    tavolo[nr][nc] = simboloGiocatore;
                }
            }
        }
    }

    /**
     * Controlla se uno dei giocatori ha perso tutte le pedine.
     *
     * @return Il giocatore che ha perso tutte le pedine, o null se nessuno ha
     *         perso.
     */
    public Giocatore controllaPedinePerse() {
        int pedineX = 0;
        int pedineO = 0;
        for (int i = 1; i < DIMENSIONE; i++) {
            for (int j = 1; j < DIMENSIONE; j++) {
                if (tavolo[i][j] == 'X') {
                    pedineX++;
                } else if (tavolo[i][j] == 'O') {
                    pedineO++;
                }
            }
        }
        if (pedineX == 0) {
            return giocatore1;
        } else if (pedineO == 0) {
            return giocatore2;
        } else {
            return null;
        }
    }

    /**
     * Controlla se la partita è finita verificando se ci sono caselle vuote sul
     * tavolo.
     *
     * @return true se la partita è finita, false altrimenti.
     */
    public boolean controllaFinePartita() {
        for (int i = 1; i < DIMENSIONE; i++) {
            for (int j = 1; j < DIMENSIONE; j++) {
                if (tavolo[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param perdente
     */
    public void mostraRisultato(final Giocatore perdente) {
        if (risultatoMostrato) {
            return;
        }
        int conteggioX = 0;
        int conteggioO = 0;

        for (int i = 1; i < DIMENSIONE; i++) {
            for (int j = 1; j < DIMENSIONE; j++) {
                if (tavolo[i][j] == 'X') {
                    conteggioX++;
                } else if (tavolo[i][j] == 'O') {
                    conteggioO++;
                }
            }
        }

        System.out.println("Risultato finale:");
        System.out.println(giocatore1.getNomeGiocatore() + ": " + conteggioX);
        System.out.println(giocatore2.getNomeGiocatore() + ": " + conteggioO);

        if (perdente == null) {
            if (conteggioX > conteggioO) {
                System.out.println(giocatore1.getNomeGiocatore() + " ha vinto!");
            } else if (conteggioO > conteggioX) {
                System.out.println(giocatore2.getNomeGiocatore() + " ha vinto!");
            } else {
                System.out.println("Pareggio!");
            }
        } else {
            Giocatore vincitore = (perdente == giocatore1) ? giocatore2 : giocatore1;
            System.out.println("Giocatore " + vincitore.getNomeGiocatore() + " ha vinto perché il giocatore "
                    + perdente.getNomeGiocatore() + " ha perso tutte le sue pedine!");
        }
        risultatoMostrato = true;
    }

    /**
     * Restituisce la storia delle mosse effettuate nella partita.
     *
     * @return La lista di stringhe che rappresenta le mosse effettuate nella
     *         partita.
     */
    public List<String> getStoriaMosse() {
        return new ArrayList<>(storiaMosse);
    }

    private boolean controllaTurno(final Giocatore giocatore) {
        char simboloGiocatore = giocatore.getGiocatoreCorrente();
        for (int riga = 1; riga < DIMENSIONE; riga++) {
            for (int colonna = 1; colonna < DIMENSIONE; colonna++) {
                if (tavolo[riga][colonna] == simboloGiocatore) {
                    if (mossaValidaA(riga, colonna, colonna, colonna, giocatore)) {
                        return true;
                    }
                    for (int dr = CONTROLLO_TURNO2; dr <= CONTROLLO_TURNO1; dr++) {
                        for (int dc = CONTROLLO_TURNO2; dc <= CONTROLLO_TURNO1; dc++) {
                            int nr = riga + dr;
                            int nc = colonna + dc;
                            if (posizioneValida(nr, nc) && tavolo[nr][nc] == '-'
                                    && mossaValidaB(riga, colonna, nr, nc, giocatore)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica se una posizione è valida (all'interno dei limiti del tavolo di
     * gioco).
     *
     * @param riga    La riga della posizione da verificare.
     * @param colonna La colonna della posizione da verificare.
     * @return true se la posizione è valida, false altrimenti.
     */
    private boolean posizioneValida(final int riga, final int colonna) {
        return riga >= 1 && riga < DIMENSIONE && colonna >= 1 && colonna < DIMENSIONE;
    }
}
