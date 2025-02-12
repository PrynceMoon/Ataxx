package it.uniba.app;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * "Boundary"
 * Classe principale dell'applicazione Ataxx.
 * Responsabilità:
 * - Gestisce l'avvio dell'applicazione.
 * - Gestisce l'interazione iniziale con l'utente.
 * - Gestisce il ciclo principale di esecuzione del gioco.
 */
public class App {

    private final Scanner scanner;
    private final Menu menu;

    /**
     * Costruttore privato per prevenire l'istanziazione.
     * Inizializza il menu e lo scanner.
     */
    public App() {
        this.menu = new Menu();
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    }

    /**
     * Restituisce il messaggio di benvenuto.
     *
     * @return Il messaggio di benvenuto.
     */
    public static String getGreeting() {
        return "Benvenuto in Ataxx";
    }

    /**
     * Metodo principale che avvia l'applicazione Ataxx.
     * Imposta la codifica dei caratteri UTF-8 per System.out.
     * Mostra il messaggio di benvenuto e avvia il gioco.
     *
     * @param args Argomenti della riga di comando (non utilizzati).
     */
    public static void main(final String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.err.println("Errore nella configurazione della codifica UTF-8.");
        }

        System.out.println(getGreeting());

        App app = new App();
        app.start();
    }

    /**
     * Avvia il gioco Ataxx.
     * Visualizza il messaggio iniziale e gestisce il loop principale del gioco.
     */
    private void start() {
        boolean continua = true;
        System.out.println("Digita comando /help o --help o -h per iniziare: ");

        while (continua) {
            System.out.println("Inserisci un comando:");
            if (scanner.hasNextLine()) {
                String comando = scanner.nextLine().trim().toLowerCase();
                continua = menu.menuGioco(comando, continua);
            }
        }
        scanner.close();
    }
}
