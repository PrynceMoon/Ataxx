package it.uniba.app;

import java.util.List;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

/*
 * Classe che gestisce informazioni e azioni del giocatore.
 */
/**
 *
 * "Entity"
 * Classe che rappresenta un giocatore nel gioco Ataxx. Responsabilità:
 * gestisce le informazioni e le azioni del giocatore, come il nome, il turno e
 * l'interazione con la partita.
 */
public class Giocatore {

    /**
     * Nome del giocatore.
     */
    private String nomeGiocatore;

    /**
     * Simbolo del giocatore corrente.
     */
    private char giocatoreCorrente;

    /**
     * Scanner per l'input dell'utente.
     */
    private Scanner scanner;

    /**
     * Tempo di inizio della partita.
     */
    private Instant start;

    private static final BloccaCella BLOCCA_CELLA = new BloccaCella();

    /**
     * Costruttore della classe Giocatore. Inizializza il giocatore corrente a 'X' e
     * il Scanner.
     */
    public Giocatore() {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.start = Instant.now();
    }

    /**
     * Costruisce un nuovo giocatore con il simbolo della pedina specificato.
     *
     * @param simboloPedina Il simbolo della pedina del giocatore (es. 'X' o 'O').
     */
    public Giocatore(final char simboloPedina) {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        giocatoreCorrente = simboloPedina;
        this.start = Instant.now();
    }

    /**
     * Chiede il nome del giocatore e lo salva.
     */
    public void chiediNomeGiocatore() {
        System.out.println("Inserisci il nome del giocatore: ");
        nomeGiocatore = scanner.nextLine().trim();
    }

    /**
     * Gestisce il turno del giocatore.
     *
     * @param partita L'oggetto Partita attuale.
     */
    public void turnoGiocatore(final Partita partita) {
        Tavolo tavolo = new Tavolo();
        Giocatore giocatoreCorrenteTurno = this;
        System.out.println("Turno di " + giocatoreCorrenteTurno.getNomeGiocatore() + " ("
                + giocatoreCorrenteTurno.getGiocatoreCorrente() + ").");

        boolean turnoInCorso = true;
        while (turnoInCorso) {
            System.out.println("Se vuoi giocare inserisci 'gioca'");
            System.out.println("Se vuoi inserire dei comandi digita 'comandi'");
            System.out.println("Se vuoi sapere quanto tempo stai giocando digita '/tempo'");
            System.out.println("Se vuoi vedere lo storico delle mosse digita '/mosse'");
            String domanda = scanner.nextLine().trim().toLowerCase();

            while (!domanda.equals("gioca") && !domanda.equals("comandi") && !domanda.equals("/tempo")
                    && !domanda.equals("/mosse")) {
                System.out.println("Risposta non valida.");
                System.out.println("Si prega di inserire 'gioca' o 'comandi' o '/tempo' o '/mosse'.");
                domanda = scanner.nextLine().trim().toLowerCase();
            }

            switch (domanda) {
                case "gioca":
                    giocatoreCorrenteTurno = partita.inserisciPedina(giocatoreCorrenteTurno);
                    if (partita.controllaFinePartita() || !partita.isPartitaInCorso()) {
                        Giocatore giocatorePerdente = partita.controllaPedinePerse(); // Ottieni il giocatore perdente
                        partita.mostraRisultato(giocatorePerdente); // Passa il giocatore perdente
                        partita.setPartitaInCorso(false);
                    }
                    turnoInCorso = false;
                    break;

                case "comandi":
                    System.out.println("I comandi che puoi digitare sono: \n/tavoliere\n/qualimosse\n/abbandona");
                    System.out.println("Inserisci il comando scelto:");
                    String comando = scanner.nextLine().trim().toLowerCase();
                    switch (comando) {
                        case "/tavoliere":
                            tavolo.tavoliere(partita.getTavolo());
                            break;
                        case "/qualimosse":
                            MosseDisponibili.qualimosse(partita.getTavolo(),
                             giocatoreCorrenteTurno.getGiocatoreCorrente());
                            break;
                        case "/abbandona":
                            abbandonaPartita(partita, scanner, giocatoreCorrenteTurno);
                            turnoInCorso = false;
                            partita.setPartitaInCorso(false);
                            break;
                        default:
                            System.out.println("Comando non riconosciuto.");
                    }
                    break;
                case "/mosse":
                    mostraStoriaMosse(partita.getStoriaMosse());
                    break;

                case "/tempo":
                    System.out.println("Tempo di gioco: " + getTimer());
                    break;
                default:
                    System.out.println("Comando non riconosciuto.");
            }
        }
    }

    /**
     * Mostra la storia delle mosse effettuate durante la partita.
     *
     * @param storiaMosse La lista delle mosse effettuate.
     */
    private void mostraStoriaMosse(final List<String> storiaMosse) {
        if (storiaMosse.isEmpty()) {
            System.out.println("Nessuna mossa registrata.");
        } else {
            for (String mossa : storiaMosse) {
                System.out.println(mossa);
            }
        }
    }

    /**
     * Restituisce il tempo trascorso dall'inizio del gioco in formato hh:mm:ss.
     *
     * @return Il tempo trascorso dall'inizio del gioco.
     */
    private String getTimer() {
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        long ore = duration.toHours();
        long minuti = duration.toMinutesPart();
        long secondi = duration.toSecondsPart();

        return String.format("%02d:%02d:%02d", ore, minuti, secondi);
    }

    /**
     * Restituisce il nome del giocatore.
     *
     * @return Il nome del giocatore.
     */
    public String getNomeGiocatore() {
        return nomeGiocatore;
    }

    /**
     * Restituisce il simbolo del giocatore corrente.
     *
     * @return Il simbolo del giocatore corrente.
     */
    public char getGiocatoreCorrente() {
        return giocatoreCorrente;
    }

    /**
     * Gestisce l'abbandono della partita da parte di un giocatore.
     *
     * @param partita           L'oggetto Partita attuale.
     * @param scanner           Lo scanner per leggere l'input dell'utente.
     * @param giocatoreCorrente Il giocatore corrente che sta abbandonando.
     */
    public static void abbandonaPartita(final Partita partita, final Scanner scanner,
            final Giocatore giocatoreCorrente) {
        System.out.println("Sei sicuro di abbandonare la partita?");
        System.out.println("Così facendo perderai la partita a tavolino.");
        String risposta = scanner.nextLine().trim().toLowerCase();
        while (!risposta.equals("si") && !risposta.equals("no")) {
            System.out.println("Risposta non valida. Si prega di inserire Si o No.");
            System.out.println("Sei sicuro di voler abbandonare la partita? (Si/No)");
            risposta = scanner.nextLine().trim().toLowerCase();
        }
        if (risposta.equals("si")) {
            String nomeGiocatoreCorrente = giocatoreCorrente.getNomeGiocatore();

            // Supponiamo che la classe Partita abbia un metodo getGiocatori che restituisce
            // un array di giocatori
            Giocatore[] giocatori = partita.getGiocatori();
            Giocatore giocatore1 = giocatori[0];
            Giocatore giocatore2 = giocatori[1];
            Giocatore avversario = (giocatoreCorrente.equals(giocatore1)) ? giocatore2 : giocatore1;

            String nomeAvversario = avversario.getNomeGiocatore();
            int pedineAvversario = contaPedine(partita.getTavolo(), avversario.getGiocatoreCorrente());

            System.out.println("Il giocatore " + nomeGiocatoreCorrente + " si è ritirato.");
            System.out.println("Ha perso " + pedineAvversario + " a 0 per " + nomeAvversario + ".");
            partita.setPartitaInCorso(false); // Imposta partitaInCorso su false

            // Ripristina il tavoliere e sblocca le celle bloccate
            BLOCCA_CELLA.ripristinaTavolo(); // Metodo che resetta lo stato del tavolo
        }
    }

    /**
     * Conta il numero di pedine di un determinato giocatore sul tavolo.
     *
     * @param tavolo Il tavolo di gioco attuale.
     * @param pedina Il simbolo del giocatore di cui contare le pedine.
     * @return Il numero di pedine del giocatore sul tavolo.
     */
    private static int contaPedine(final char[][] tavolo, final char pedina) {
        int conteggio = 0;
        for (int i = 1; i < tavolo.length; i++) {
            for (int j = 1; j < tavolo[i].length; j++) {
                if (tavolo[i][j] == pedina) {
                    conteggio++;
                }
            }
        }
        return conteggio;
    }
}
