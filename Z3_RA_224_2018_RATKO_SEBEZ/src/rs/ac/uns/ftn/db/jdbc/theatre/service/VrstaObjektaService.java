package rs.ac.uns.ftn.db.jdbc.theatre.service;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.theatre.dao.VrstaObjektaDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.impl.VrstaObjektaDAOImpl;

public class VrstaObjektaService {
	private static final VrstaObjektaDAO vrstaObjektaDAO = new VrstaObjektaDAOImpl();
	
	public String getNazivVrsteProjekta(Long id) throws SQLException{
		return vrstaObjektaDAO.getNazivVrsteObjekta(id);
	}
}
