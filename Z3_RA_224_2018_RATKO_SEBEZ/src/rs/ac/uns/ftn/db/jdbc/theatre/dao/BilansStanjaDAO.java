package rs.ac.uns.ftn.db.jdbc.theatre.dao;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.theatre.model.BilansStanja;

public interface BilansStanjaDAO extends CRUDDao<BilansStanja, Long> {
	public void payDebt(Long id, Double ammount) throws SQLException;
}
