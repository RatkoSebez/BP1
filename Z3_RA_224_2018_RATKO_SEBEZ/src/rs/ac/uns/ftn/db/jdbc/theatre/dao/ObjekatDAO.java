package rs.ac.uns.ftn.db.jdbc.theatre.dao;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.theatre.dto.ComplexQuery1;
import rs.ac.uns.ftn.db.jdbc.theatre.model.Objekat;

public interface ObjekatDAO extends CRUDDao<Objekat, Long> {
	public Iterable<ComplexQuery1> doObjekatComplexQuery() throws SQLException;
	public Iterable<String> getZaduzeniRadniciVrsteObjekta(Long idvo) throws SQLException;
	public Iterable<Objekat> findByIdLica(String id) throws SQLException ;
}
