# Ataxx

Ataxx è un gioco da tavolo strategico per due giocatori in cui l'obiettivo è conquistare il maggior numero possibile di caselle sul tabellone. Questo progetto implementa il gioco in Java, offrendo una solida struttura per la logica di gioco, la gestione dei turni e il controllo dello stato della scacchiera.

## Caratteristiche principali

- **Logica di gioco completa**:  
  - Gestione del tabellone (di dimensione tipica 7x7 o configurabile) e delle caselle.
  - Meccanismi di duplicazione e salto delle pedine.
  - Conversione automatica delle pedine avversarie adiacenti a quelle mosse.
  - Verifica delle condizioni di fine partita (quando il tabellone è pieno o non sono più possibili mosse).

- **Struttura modulare**:  
  - Il codice è organizzato nel package `it.uniba.app`, in modo da mantenere una chiara separazione delle responsabilità.
  - Le classi sono suddivise per gestire in modo indipendente la logica di gioco, il modello del tabellone, i giocatori e le mosse.

## Descrizione dei file principali

Di seguito una panoramica delle classi (presenti nella cartella `src/main/java/it/uniba/app`) e delle loro responsabilità:

- **`App.java`**  
  *Punto di ingresso dell'applicazione.*  
  Contiene il metodo `main()`, che avvia il gioco e inizializza la base per effettuare la partita o eseguire dei comandi prima della partita, applicando una logica più complessa al gioco.

- **`BloccaCella.java`**  
  *Gestione del tabellone di gioco inserendo delle celle che non possono essere popolate dalle pedine dei giocatori.*  
  Si offre la possibilità di bloccare delle celle del tabellone, in questo modo le celle bloccate non potranno essere popolate dalle pedine dei giocatori, questo meccanismo può essere eseguito per un massimo di 9 volte ad ogni partita.

- **`Giocatore.java`**  
  *Inizializzazione dei giocatori.*  
  Qui vengono inizializzati i giocatori che effettueranno la partita, durante l'esecuzione di quest'ultima i giocatori applicheranno dei comandi per andar avanti durante la partita.

- **`Menu.java`**  
  *Inizializzazione dei comandi.*  
  Qui vengono inizializzati tutti i comandi che i giocatori potranno eseguire durante il proprio turno.

- **`MosseDisponibili.java`**  
  *Gestione delle mosse.*  
  Con la chiamata di essa, in base al turno del giocatore corrente è possibilie far visualizzare ad esso tutte le mosse possibili che può eseguire con ogni pedina in suo possesso.

  - **`Partita.java`**  
  *Logica della partita.*  
  Coordina il flusso della partita, gestisce i turni dei giocatori, controlla le condizioni di vittoria e determina quando la partita è conclusa.

- **`Tavolo.java`**  
  *Inizializzazione e gestione del tabellone di gioco.*  
  Qui viene inizializzato il tavolo di gioco con le 4 pedine(2 per ogni giocatore) agli angoli, tutte le funzioni di stampa del tavolo sia senza le celle bloccate che con le celle bloccate.

## Requisiti e installazione

Per compilare ed eseguire il progetto sono necessari:
- **Java JDK 8 (o superiore)**
- (Eventuale) **Maven/Gradle** se il progetto è configurato con un sistema di build; in questo caso, consulta il file `pom.xml` o `build.gradle` presente nella root del progetto.

### Compilazione ed esecuzione da linea di comando

Se utilizzi la compilazione manuale, puoi procedere come segue:
  
```bash
# Compilazione (assumendo di trovarti nella root del progetto)
javac -d bin src/main/java/it/uniba/app/*.java

# Esecuzione
java -cp bin it.uniba.app.Ataxx
