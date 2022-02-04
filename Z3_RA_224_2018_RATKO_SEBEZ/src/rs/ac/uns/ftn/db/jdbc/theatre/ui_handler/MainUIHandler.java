package rs.ac.uns.ftn.db.jdbc.theatre.ui_handler;

import java.util.Scanner;

public class MainUIHandler {
	public static Scanner sc = new Scanner(System.in);
	private final BilansStanjaUIHandler bilansStanjaUIHandler = new BilansStanjaUIHandler();
	private final LiceUIHandler liceUIHandler = new LiceUIHandler();
	private final ObjekatUIHandler objekatUIHandler = new ObjekatUIHandler(); 
	
	public void handleMainMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju: ");
			System.out.println("1 - Rukovanje bilansom stanja");
			System.out.println("2 - Rukovanje licima");
			System.out.println("3 - Rukovanje objektima");
			System.out.println("X - Izlazak iz programa");
			answer = sc.nextLine();
			
			switch(answer) {
			case "1":
				bilansStanjaUIHandler.handleBilansStanjaMenu();
				break;
			case "2":
				liceUIHandler.handleLiceMenu();
				break;
			case "3":
				objekatUIHandler.handleObjekatMenu();
				break;
			}
		} while(!answer.equalsIgnoreCase("X"));
		sc.close();
	}
	
}
