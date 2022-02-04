package rs.ac.uns.ftn.db.jdbc.theatre.dao;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.theatre.model.VrstaObjekta;

public interface VrstaObjektaDAO extends CRUDDao<VrstaObjekta, Long> {
	public String getNazivVrsteObjekta(Long id) throws SQLException;
}
