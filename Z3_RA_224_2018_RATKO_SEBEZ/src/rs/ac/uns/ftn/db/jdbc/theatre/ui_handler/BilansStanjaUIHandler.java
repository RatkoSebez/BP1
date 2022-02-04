package rs.ac.uns.ftn.db.jdbc.theatre.ui_handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.theatre.model.BilansStanja;
import rs.ac.uns.ftn.db.jdbc.theatre.service.BilansStanjaService;

public class BilansStanjaUIHandler {
	private static final BilansStanjaService bilansStanjaService = new BilansStanjaService();
	
	public void handleBilansStanjaMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad bilansima stanja: ");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog bilansa stanja");
			System.out.println("4 - Unos vise bilansa stanja");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("7 - Otplata duga");
			System.out.println("X - Izlazak iz programa");
			answer = MainUIHandler.sc.nextLine();
			
			switch(answer) {
			case "1":
				showAll();
				break;
			case "2":
				showById();
				break;
			case "3":
				handleSingleInsert();
				break;
			case "4":
				handleMultipleInserts();
				break;
			case "5":
				handleUpdate();
				break;
			case "6":
				handleDelete();
				break;
			case "7":
				payDebt();
				break;
			}
		} while(!answer.equalsIgnoreCase("X"));
	}
	
	private void showAll() {
		System.out.println(BilansStanja.getFormattedHeader());
		
		try {
			for(BilansStanja bilans : bilansStanjaService.getAll()) {
				System.out.println(bilans);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	private void showById() {
		System.out.println("ID bilansa stanja: ");
		Long id = Long.parseLong(MainUIHandler.sc.nextLine());

		try {
			BilansStanja bilans = bilansStanjaService.getById(id);
			System.out.println(BilansStanja.getFormattedHeader());
			System.out.println(bilans);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleSingleInsert() {
		System.out.println("ID Bilansa Stanja: ");
		Long idbs = Long.parseLong(MainUIHandler.sc.nextLine());

		System.out.println("ID Lica: ");
		String idLica = MainUIHandler.sc.nextLine();

		System.out.println("Saldo: ");
		Double saldo = Double.parseDouble(MainUIHandler.sc.nextLine());

		System.out.println("Dug: ");
		Double dug = Double.parseDouble(MainUIHandler.sc.nextLine());
		
		System.out.println("Kamata: ");
		Double kamata = Double.parseDouble(MainUIHandler.sc.nextLine());

		try {
			bilansStanjaService.save(new BilansStanja(idbs, idLica, saldo, dug, kamata));
			System.out.println("Dodavanje uspesno.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleUpdate() {
		System.out.println("ID bilansa stanja: ");
		Long idbs = Long.parseLong(MainUIHandler.sc.nextLine());

		try {
			if (!bilansStanjaService.existsById(idbs)) {
				System.out.println("Uneta vrednost ne postoji!");
				return;
			}

			System.out.println("ID Lica: ");
			String idLica = MainUIHandler.sc.nextLine();

			System.out.println("Saldo: ");
			Double saldo = Double.parseDouble(MainUIHandler.sc.nextLine());

			System.out.println("Dug: ");
			Double dug = Double.parseDouble(MainUIHandler.sc.nextLine());
			
			System.out.println("Kamata: ");
			Double kamata = Double.parseDouble(MainUIHandler.sc.nextLine());

			boolean success = bilansStanjaService.save(new BilansStanja(idbs, idLica, saldo, dug, kamata));
			if (success) {
				System.out.println("Bilansi su uspesno dodati.");
			} else {
				System.out.println("Dodavanje bilansa nije uspelo.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleDelete() {
		System.out.println("ID bilansa stanja: ");
		Long id = Long.parseLong(MainUIHandler.sc.nextLine());

		try {
			boolean success = bilansStanjaService.deleteById(id);
			if (success) {
				System.out.println("Bilans uspesno obrisan.");
			} else {
				System.out.println("Brisanje bilansa nije uspelo.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleMultipleInserts() {
		List<BilansStanja> bilansList = new ArrayList<>();
		String answer;
		do {
			System.out.println("ID Bilansa Stanja: ");
			Long idbs = Long.parseLong(MainUIHandler.sc.nextLine());

			System.out.println("ID Lica: ");
			String idLica = MainUIHandler.sc.nextLine();

			System.out.println("Saldo: ");
			Double saldo = Double.parseDouble(MainUIHandler.sc.nextLine());

			System.out.println("Dug: ");
			Double dug = Double.parseDouble(MainUIHandler.sc.nextLine());
			
			System.out.println("Kamata: ");
			Double kamata = Double.parseDouble(MainUIHandler.sc.nextLine());
			
			bilansList.add(new BilansStanja(idbs, idLica, saldo, dug, kamata));
			
			System.out.println("Prekinuti unos? X za potvrdu");
			answer = MainUIHandler.sc.nextLine();
		} while (!answer.equalsIgnoreCase("X"));

		try {
			int rowsSaved = bilansStanjaService.saveAll(bilansList);
			System.out.println("Uspesno a�urirano " + rowsSaved + " bilansa.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void payDebt() {
		System.out.println("ID bilansa: ");
		Long id = Long.parseLong(MainUIHandler.sc.nextLine());
		System.out.println("Iznos u dinarima: ");
		double ammount = Double.parseDouble(MainUIHandler.sc.nextLine());
		try {
			bilansStanjaService.payDebt(id, ammount);
			System.out.println("Uspesno otplacen dug");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
