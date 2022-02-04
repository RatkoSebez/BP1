package rs.ac.uns.ftn.db.jdbc.theatre.ui_handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.theatre.dto.ComplexQuery1;
import rs.ac.uns.ftn.db.jdbc.theatre.model.Lice;
import rs.ac.uns.ftn.db.jdbc.theatre.model.Objekat;
import rs.ac.uns.ftn.db.jdbc.theatre.service.LiceService;
import rs.ac.uns.ftn.db.jdbc.theatre.service.ObjekatService;
import rs.ac.uns.ftn.db.jdbc.theatre.service.VrstaObjektaService;

public class ObjekatUIHandler {
	private static final ObjekatService objekatService = new ObjekatService();
	private static final VrstaObjektaService vrstaObjektaService = new VrstaObjektaService();
	private static final LiceService liceService = new LiceService();
	
	public void handleObjekatMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad objektima: ");
			System.out.println("1 - Prikaz izveštaja koji će za svaku vrstu objekta prikazati naziv vrste\r\n"
					+ "objekta, broj lica koja su zadužena za tu vrstu objekta, ukupnu površinu i ukupnu\r\n"
					+ "vrednost za tu vrstu objekta, kao i prosečnu cenu kvadrata za tu vrstu objekta. Za svaku\r\n"
					+ "vrstu objekta, ispisana su i lica koja su zadužena za tu vrstu objekta");
			System.out.println("2 - Prikaz izveštaja koji će za unetu vrednost mesečne plate prikazati sve\r\n"
					+ "objekte koji pripadaju licima čija je plata manja od zadate. Nakon liste objekata\r\n"
					+ "prikazuje se prosečna cena tih objekata, izraženo u evrima.");
			System.out.println("X - Izlazak iz programa");
			answer = MainUIHandler.sc.nextLine();
			
			switch(answer) {
			case "1":
				showAllByComplexQuery();
				break;
			case "2":
				showAllByComplexQuery2();
				break;
			}
		} while(!answer.equalsIgnoreCase("X"));
	}
	
	private void showAllByComplexQuery() {
		try {
			for(ComplexQuery1 cq: objekatService.getAllFromComplexQuery()) {
				System.out.println(ComplexQuery1.getFormattedHeader());
				System.out.println(cq.toString(vrstaObjektaService.getNazivVrsteProjekta(cq.getIdo())));
				System.out.println(Lice.getFormattedHeader());
				for(String s: objekatService.getZaduzeniRadniciVrsteObjekta(cq.getIdo())) {
					System.out.println(liceService.getById(s));
				}
				System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	private void showAllByComplexQuery2() {
		System.out.println("Plata: ");
		Double plata = Double.parseDouble(MainUIHandler.sc.nextLine());
		List<Objekat> objekti = new ArrayList<Objekat>();
		double cena = 0;
		
		try {
			for(Lice lice : liceService.getAllByPlata(plata)) {
				// System.out.println(lice);
				ArrayList<Objekat> pom = objekatService.findByIdLica(lice.getIdl());
				for(Objekat obj : pom) {
					objekti.add(obj);
				}
			}
			for(Objekat objekat : objekti) {
				cena += objekat.getVrednost();
				System.out.println(objekat);
			}
			System.out.println("Prosecna cena objekata: " + cena/objekti.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
