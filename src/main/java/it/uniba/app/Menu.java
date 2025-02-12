package it.uniba.app;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * "Boundary" Classe che gestisce il menu dell'applicazione Ataxx.
 * Responsabilità: fornisce un'interfaccia per l'interazione con l'utente,
 * gestisce i comandi del menu e avvia le azioni appropriate.
 */
public class Menu {

    private final Partita partita;
    private final Tavolo tavolo;
    private final BloccaCella blocca;

    /**
     * Costruttore della classe Menu.
     */
    public Menu() {
        this.partita = new Partita();
        this.tavolo = new Tavolo();
        this.blocca = new BloccaCella();
    }

    /**
     * Stampa i comandi disponibili per l'utente.
     * Questo metodo elenca i comandi che l'utente può utilizzare
     * per interagire con il menu dell'applicazione.
     */
    public void listaComandi() {
        System.out.println("Ecco i comandi disponibili:");
        System.out.println(" - /blocca <coordinata> - Blocca delle caselle rendendole non accessibili");
        System.out.println(" - /gioca - Inizia una nuova partita");
        System.out.println(" - /vuoto - Crea un tavoliere vuoto");
        System.out.println(" - /tavoliere - Mostra lo stato attuale del tavoliere");
        System.out.println(" - /qualimosse - Mostra le mosse disponibili");
        System.out.println(" - /abbandona - Abbandona la partita corrente");
        System.out.println(" - /esci - Esci dal gioco");
    }

    /**
     * Gestisce i comandi del menu.
     * Questo metodo prende il comando inserito dall'utente e lo esegue,
     * modificando eventualmente lo stato del flag "termina".
     *
     * @param comando il comando inserito dall'utente.
     * @param termina lo stato attuale del flag "termina".
     * @return lo stato aggiornato del flag "termina".
     */
    public boolean menuGioco(final String comando, final boolean termina) {
        boolean nuovoTermina = termina;
        String[] parts = comando.split(" ");
        String cmd = parts[0];
        switch (cmd) {
            case "/help":
            case "--help":
            case "-h":
                listaComandi();
                break;
            case "/gioca":
                // Avvia la partita senza coinvolgere BloccaCella
                partita.iniziaPartita();
                break;
            case "/blocca":
                if (parts.length > 1) {
                    String coordinata = parts[1];
                    // Blocca la cella specificata solo se richiesto
                    blocca.bloccaCella(coordinata);
                } else {
                    System.out.println("Errore: coordinata non specificata. Utilizzo corretto: /blocca <coordinata>");
                }
                break;
            case "/vuoto":
                // Mostra il tabellone vuoto senza coinvolgere BloccaCella
                tavolo.stampaVuoto();
                break;
            case "/tavoliere":
            case "/qualimosse":
            case "/abbandona":
                System.out.println("Partita non trovata.\n Esegui il comando '/gioca' e digita il comando");
                break;
            case "/esci":
                try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
                    System.out.println("Sei sicuro di voler chiudere il gioco?");
                    String risposta = scanner.nextLine().trim().toLowerCase();
                    while (!risposta.equals("si") && !risposta.equals("no")) {
                        System.out.println("Risposta non valida. Si prega di inserire Si o No.");
                        System.out.println("Sei sicuro di voler chiudere il gioco? (Si/No)");
                        risposta = scanner.nextLine().trim().toLowerCase();
                    }
                    if (risposta.equals("si")) {
                        nuovoTermina = false;
                        System.out.println("Speriamo che ti sia divertito.");
                        System.out.println("Arrivederci :(");
                    }
                }
                break;
            default:
                System.out.println("Comando non riconosciuto. Digita '/help' per vedere la lista dei comandi.");
        }
        return nuovoTermina;
    }
}
