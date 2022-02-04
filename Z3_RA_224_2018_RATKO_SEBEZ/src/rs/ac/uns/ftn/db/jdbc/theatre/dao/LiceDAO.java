package rs.ac.uns.ftn.db.jdbc.theatre.dao;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.theatre.model.Lice;

public interface LiceDAO extends CRUDDao<Lice, String> {
	public Iterable<Lice> findAllByVrsta(String vrsta) throws SQLException;
	public Iterable<Lice> findAllByPlata(Double plata) throws SQLException;
}
