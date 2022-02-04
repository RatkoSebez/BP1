package rs.ac.uns.ftn.db.jdbc.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.theatre.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.ObjekatDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.dto.ComplexQuery1;
import rs.ac.uns.ftn.db.jdbc.theatre.model.Objekat;

public class ObjekatDAOImpl implements ObjekatDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Objekat entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Objekat> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Objekat> findAllById(Iterable<Long> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Objekat findById(Long id) throws SQLException {
		return null;
	}

	@Override
	public boolean save(Objekat entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int saveAll(Iterable<Objekat> entities) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<ComplexQuery1> doObjekatComplexQuery() throws SQLException {
		List<ComplexQuery1> cqObjekti = new ArrayList<>();

		String query = "select idvo, count(idl), sum(povrsina), sum(vrednost), sum(vrednost)/sum(povrsina) from objekat group by idvo";
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
//				Objekat objekat = new Objekat(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getDouble(6));
				//Objekat objekat = new Objekat(resultSet.getLong(1), "", 1l, 0.0, "", 0.1);
				ComplexQuery1 cq = new ComplexQuery1(resultSet.getLong(1), resultSet.getInt(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getDouble(5));
				
				cqObjekti.add(cq);
			}
		}
		
		return cqObjekti;
	}

	@Override
	public Iterable<String> getZaduzeniRadniciVrsteObjekta(Long idvo) throws SQLException {
		List<String> ids = new ArrayList<String>();
		String query = "select idl from objekat where idvo = ?";
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, idvo);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ids.add(resultSet.getString(1));
			}
		}
		
		return ids;
	}

	public ArrayList<Objekat> findByIdLica(String id) throws SQLException {
		String query = "select ido, idl, idvo, povrsina, adresa, vrednost from objekat where idl = ?";
		ArrayList<Objekat> objekti = new ArrayList<Objekat>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while(resultSet.next()) {
					objekti.add(new Objekat(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getDouble(6)));
				}
			}
		}

		return objekti;
	}
}
