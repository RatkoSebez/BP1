package rs.ac.uns.ftn.db.jdbc.theatre.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.jdbc.theatre.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.jdbc.theatre.dao.BilansStanjaDAO;
import rs.ac.uns.ftn.db.jdbc.theatre.model.BilansStanja;

public class BilansStanjaDAOImpl implements BilansStanjaDAO{

	@Override
	public int count() throws SQLException {
		String query = "select count(*) from bilansstanja";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return -1;
			}
		}
	}

	@Override
	public boolean delete(BilansStanja entity) throws SQLException {
		return deleteById(entity.getIdbs());
	}

	@Override
	public int deleteAll() throws SQLException {
		String query = "delete from bilansstanja";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			int rowsAfffected = preparedStatement.executeUpdate();
			return rowsAfffected;
		}

	}

	@Override
	public boolean deleteById(Long id) throws SQLException {
		String query = "delete from bilansstanja where idbs=?";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}

	}

	@Override
	public boolean existsById(Long id) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return existsByIdTransactional(connection, id);
		}
	}

	// connection is a parameter because this method is used in a transaction (see
	// saveAll method)
	private boolean existsByIdTransactional(Connection connection, Long id) throws SQLException {
		String query = "select * from bilansstanja where idbs=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}

	@Override
	public Iterable<BilansStanja> findAll() throws SQLException {
		String query = "select idbs, idl, saldo, dug, kamata from bilansstanja";
		List<BilansStanja> bilansStanjaList = new ArrayList<BilansStanja>();
		Connection connection = ConnectionUtil_HikariCP.getConnection();
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			while(resultSet.next()) {
				BilansStanja bilansStanja = new BilansStanja(resultSet.getLong(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getDouble(5));
				bilansStanjaList.add(bilansStanja);
			}
		}
		return bilansStanjaList;
	}

	@Override
	public Iterable<BilansStanja> findAllById(Iterable<Long> ids) throws SQLException {
		List<BilansStanja> theatreList = new ArrayList<>();

		StringBuilder stringBuilder = new StringBuilder();

		String queryBegin = "select idbs, idl, saldo, dug, kamata from bilansstanja where idbs in(";
		stringBuilder.append(queryBegin);

		for (@SuppressWarnings("unused")
		Long id : ids) {
			stringBuilder.append("?,");
		}

		stringBuilder.deleteCharAt(stringBuilder.length() - 1); // delete last ','
		stringBuilder.append(")");

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());) {

			int i = 0;
			for (Long id : ids) {
				preparedStatement.setLong(++i, id);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					theatreList.add(new BilansStanja(resultSet.getLong(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getDouble(5)));
				}
			}
		}

		return theatreList;
	}

	@Override
	public BilansStanja findById(Long id) throws SQLException {
		String query = "select idbs, idl, saldo, dug, kamata from bilansstanja where idbs = ?";
		BilansStanja pozoriste = null;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.isBeforeFirst()) {
					resultSet.next();

					pozoriste = new BilansStanja(resultSet.getLong(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getDouble(5));
				}
			}
		}

		return pozoriste;
	}

	@Override
	public boolean save(BilansStanja entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<BilansStanja> entities) throws SQLException {
		
		int rowsSaved = 0;
		
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			connection.setAutoCommit(false); // transaction start

			// insert or update every theatre
			for (BilansStanja entity : entities) {
				boolean success = saveTransactional(connection, entity); // changes are visible only to current connection
				if (success) rowsSaved++;
			}

			connection.commit(); // transaction ends successfully, changes are now visible to other connections as well
		}
		
		return rowsSaved;
	}

	// used by save and saveAll
	private boolean saveTransactional(Connection connection, BilansStanja entity) throws SQLException {
		// id_th intentionally in the last place, so that the order between commands remains the same
		String insertCommand = "insert into bilansstanja (idbs, idl, saldo, dug, kamata) values (?, ? , ?, ?,?)";
		String updateCommand = "update bilansstanja set idbs=?, idl=?, saldo=?, dug=?, kamata=? where idbs=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				existsByIdTransactional(connection, entity.getIdbs()) ? updateCommand : insertCommand)) {
			int i = 1;
			preparedStatement.setLong(i++, entity.getIdbs());
			preparedStatement.setString(i++, entity.getIdl());
			preparedStatement.setDouble(i++, entity.getSaldo());
			preparedStatement.setDouble(i++, entity.getDug());
			preparedStatement.setDouble(i++, entity.getKamata());
			boolean ok = false;
			if(existsByIdTransactional(connection, entity.getIdbs()) ? ok = true : ok) {
				preparedStatement.setLong(i++, entity.getIdbs());
			}
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}

	@Override
	public void payDebt(Long id, Double ammount) throws SQLException {
		BilansStanja bilans = findById(id);
		bilans.setSaldo(bilans.getSaldo() + ammount);
		double tmp = ammount;
		ammount -= bilans.getKamata();
		bilans.setKamata(Math.max(0, bilans.getKamata() - tmp));
		tmp = ammount;
		if(ammount > 0) {
			ammount -= bilans.getDug();
			bilans.setDug(Math.max(0, bilans.getDug() - tmp));
		}
		save(bilans);
	}

}
