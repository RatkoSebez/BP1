package rs.ac.uns.ftn.db.jdbc.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import rs.ac.uns.ftn.db.jdbc.theatre.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.VrstaObjektaDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.model.VrstaObjekta;

public class VrstaObjektaDAOImpl implements VrstaObjektaDAO {

	@Override
	public int count() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(VrstaObjekta entity) throws SQLException {
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
	public Iterable<VrstaObjekta> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<VrstaObjekta> findAllById(Iterable<Long> ids) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VrstaObjekta findById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(VrstaObjekta entity) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int saveAll(Iterable<VrstaObjekta> entities) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNazivVrsteObjekta(Long id) throws SQLException {
		String query = "select nazivvo from vrstaobjekta where idvo = ?";
		String naziv;
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
				resultSet.next();
				naziv = resultSet.getString(1);
		}
		return naziv;
	}
}
