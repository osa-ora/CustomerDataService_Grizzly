package osa.ora.customer.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import osa.ora.DBConnection;
import osa.ora.customer.exception.JsonMessage;
import osa.ora.beans.Accounts;

public class CustomerAccountsPersistence {

	private Connection conn = null;

	public CustomerAccountsPersistence() {
		conn = DBConnection.getInstance().getConnection();

	}

	public JsonMessage save(Accounts account) {
		String insertTableSQL = "INSERT INTO CUSTOMER_ACCOUNT "
				+ "(CUSTOMER_ID, ACCOUNT_NO, TYPE) "
				+ "VALUES(?,?,?)";

		try (PreparedStatement preparedStatement = this.conn
				.prepareStatement(insertTableSQL)) {

			preparedStatement.setLong(1, account.getId());
			preparedStatement.setString(2, account.getAccountNumber());
			preparedStatement.setString(3, account.getAccountType());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			return new JsonMessage("Error", "Customer Account add failed: "
					+ e.getMessage());
		} catch (Exception e) {
			return new JsonMessage("Error", "Customer Account add failed: "
					+ e.getMessage());
		}
		return new JsonMessage("Success", "Customer Account add succeeded...");
	}

	public JsonMessage delete(long id, String account_no) {
		String deleteRowSQL = "DELETE FROM CUSTOMER_ACCOUNT WHERE CUSTOMER_ID=? and ACCOUNT_NO=?";
		try (PreparedStatement preparedStatement = this.conn
				.prepareStatement(deleteRowSQL)) {
			preparedStatement.setLong(1, id);
			preparedStatement.setString(2, account_no);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			return new JsonMessage("Error", "Customer Account delete failed: "
					+ e.getMessage());
		} catch (Exception e) {
			return new JsonMessage("Error", "Customer Account delete failed: "
					+ e.getMessage());
		}
		return new JsonMessage("Success", "Customer Account delete succeeded...");
	}

	public Accounts[] findAll() {
		String queryStr = "SELECT * FROM CUSTOMER_ACCOUNTS";
		return this.query(queryStr);
	}

	public Accounts[] findbyId(long id) {
		String queryStr = "SELECT * FROM CUSTOMER_ACCOUNTS WHERE CUSTOMER_ID=" + id;
		Accounts accounts[] = this.query(queryStr);
		return accounts;
	}

	public Accounts[] query(String sqlQueryStr) {
		ArrayList<Accounts> cList = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement(sqlQueryStr)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				cList.add(new Accounts(rs.getLong("CUSTOMER_ID"), rs
						.getString("ACCOUNT_NO"), rs.getString("TYPE")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cList.toArray(new Accounts[0]);
	}
}
