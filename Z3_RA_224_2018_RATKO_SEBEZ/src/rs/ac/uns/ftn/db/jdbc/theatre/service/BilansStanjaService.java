package rs.ac.uns.ftn.db.jdbc.theatre.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.theatre.dao.BilansStanjaDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.impl.BilansStanjaDAOImpl;
import rs.ac.uns.ftn.db.jdbc.theatre.model.BilansStanja;

public class BilansStanjaService {
	private static final BilansStanjaDAO bilansStanjaDAO = new BilansStanjaDAOImpl();

	public ArrayList<BilansStanja> getAll() throws SQLException{
		return (ArrayList<BilansStanja>) bilansStanjaDAO.findAll();
	}
	
	public BilansStanja getById(Long id) throws SQLException {
		return bilansStanjaDAO.findById(id);
	}

	public boolean existsById(Long id) throws SQLException {
		return bilansStanjaDAO.existsById(id);
	}

	public boolean save(BilansStanja p) throws SQLException {
		return bilansStanjaDAO.save(p);
	}

	public boolean deleteById(Long id) throws SQLException {
		return bilansStanjaDAO.deleteById(id);
	}

	public int saveAll(List<BilansStanja> bilansList) throws SQLException {
		return bilansStanjaDAO.saveAll(bilansList);
	}
	
	public void payDebt(Long id, Double ammount) throws SQLException {
		bilansStanjaDAO.payDebt(id, ammount);
	}
}
