package rs.ac.uns.ftn.db.jdbc.theatre.service;

import java.sql.SQLException;
import java.util.ArrayList;

import rs.ac.uns.ftn.db.jdbc.theatre.dao.LiceDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.impl.LiceDAOImpl;
import rs.ac.uns.ftn.db.jdbc.theatre.model.Lice;

public class LiceService {
	private static final LiceDAO liceDAO = new LiceDAOImpl();
	
	public ArrayList<Lice> getAllByVrsta(String vrsta) throws SQLException{
		return (ArrayList<Lice>) liceDAO.findAllByVrsta(vrsta);
	}
	
	public Lice getById(String idl) throws SQLException{
		return liceDAO.findById(idl);
	}
	
	public ArrayList<Lice> getAllByPlata(Double plata) throws SQLException{
		return (ArrayList<Lice>) liceDAO.findAllByPlata(plata);
	}
}
