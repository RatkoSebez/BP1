package rs.ac.uns.ftn.db.jdbc.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.theatre.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.LiceDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.model.Lice;

public class LiceDAOImpl implements LiceDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Lice entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteById(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsById(String id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Lice> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Lice> findAllById(Iterable<String> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lice findById(String idl) throws SQLException {
		String query = "select idl, imel, przl, vrstal, mes_prihodil from lice where idl = ?";
		Lice lice = new Lice();
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, idl);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				lice = new Lice(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5));
			}
		}
			
		return lice;
	}

	@Override
	public boolean save(Lice entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int saveAll(Iterable<Lice> entities) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<Lice> findAllByVrsta(String vrsta) throws SQLException {
		List<Lice> lica = new ArrayList<>();

		String query = "select idl, imel, przl, vrstal, mes_prihodil from lice where vrstal = ?";
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, vrsta);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Lice lice = new Lice(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5));
				lica.add(lice);
			}
		}
		
		return lica;
	}

	@Override
	public Iterable<Lice> findAllByPlata(Double plata) throws SQLException {
		List<Lice> lica = new ArrayList<>();

		String query = "select idl, imel, przl, vrstal, mes_prihodil from lice where mes_prihodil > ?";
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setDouble(1, plata);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Lice lice = new Lice(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5));
				lica.add(lice);
			}
		}
		
		return lica;
	}
}
