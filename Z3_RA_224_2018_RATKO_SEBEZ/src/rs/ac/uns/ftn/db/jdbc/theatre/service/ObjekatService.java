package rs.ac.uns.ftn.db.jdbc.theatre.service;

import java.sql.SQLException;
import java.util.ArrayList;

import rs.ac.uns.ftn.db.jdbc.theatre.dao.ObjekatDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.impl.ObjekatDAOImpl;
import rs.ac.uns.ftn.db.jdbc.theatre.dto.ComplexQuery1;
import rs.ac.uns.ftn.db.jdbc.theatre.model.Objekat;

public class ObjekatService {
	private static final ObjekatDAO objekatDAO = new ObjekatDAOImpl();
	
	public ArrayList<ComplexQuery1> getAllFromComplexQuery() throws SQLException{
		return (ArrayList<ComplexQuery1>) objekatDAO.doObjekatComplexQuery();
	}
	
	public ArrayList<String> getZaduzeniRadniciVrsteObjekta(Long idvo) throws SQLException{
		return (ArrayList<String>) objekatDAO.getZaduzeniRadniciVrsteObjekta(idvo);
	}
	
	public ArrayList<Objekat> findByIdLica(String id) throws SQLException{
		return (ArrayList<Objekat>) objekatDAO.findByIdLica(id);
	}
}
