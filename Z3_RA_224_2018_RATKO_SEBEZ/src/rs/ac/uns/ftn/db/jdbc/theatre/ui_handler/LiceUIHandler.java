package rs.ac.uns.ftn.db.jdbc.theatre.ui_handler;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.theatre.model.Lice;
import rs.ac.uns.ftn.db.jdbc.theatre.service.LiceService;

public class LiceUIHandler {
	private static final LiceService liceService = new LiceService();
	
	public void handleLiceMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad licima: ");
			System.out.println("1 - Prikaz svih lica po vrsti");
			System.out.println("X - Izlazak iz programa");
			answer = MainUIHandler.sc.nextLine();
			
			switch(answer) {
			case "1":
				showAllByVrsta();
				break;
			}
		} while(!answer.equalsIgnoreCase("X"));
	}
	
	private void showAllByVrsta() {
		System.out.println("Vrsta lica: ");
		String vrsta = MainUIHandler.sc.nextLine();
		Double ukupniPrihodi = 0.0;
		int size = 0;
		
		System.out.println(Lice.getFormattedHeader());
		try {
			for(Lice lica : liceService.getAllByVrsta(vrsta)) {
				size++;
				System.out.println(lica);
				ukupniPrihodi += lica.getMes_prihodi();
			}
			System.out.println("Prosecni mesecni prihodi u dinarima: " + ukupniPrihodi/size);
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
}
