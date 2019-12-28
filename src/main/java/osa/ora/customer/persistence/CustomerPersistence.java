package osa.ora.customer.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import osa.ora.DBConnection;
import osa.ora.customer.exception.JsonMessage;
import osa.ora.beans.Customer;

public class CustomerPersistence {

    private Connection conn = null;

    public CustomerPersistence() {
        conn = DBConnection.getInstance().getConnection();

    }

    public JsonMessage save(Customer customer) {
        String insertTableSQL = "INSERT INTO CUSTOMER "
                + "(FIRSTNAME, LASTNAME, EMAIL, CITY, STATE, BIRTHDAY) "
                + "VALUES(?,?,?,?,?,?)";
        //+ "VALUES(CUSTOMER_SEQ.NEXTVAL,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = this.conn
                .prepareStatement(insertTableSQL)) {

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getCity());
            preparedStatement.setString(5, customer.getState());
            preparedStatement.setString(6, customer.getBirthday());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return new JsonMessage("Error", "Customer add failed: "
                    + e.getMessage());
        } catch (Exception e) {
            return new JsonMessage("Error", "Customer add failed: "
                    + e.getMessage());
        }
        return new JsonMessage("Success", "Customer add succeeded...");
    }

    public JsonMessage update(Customer customer) {
        String updateTableSQL = "UPDATE CUSTOMER SET FIRSTNAME= ?, LASTNAME= ?,   EMAIL=?,  CITY=?, STATE=?, BIRTHDAY=?  WHERE ID=?";
        try (PreparedStatement preparedStatement = this.conn
                .prepareStatement(updateTableSQL);) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getCity());
            preparedStatement.setString(5, customer.getState());
            preparedStatement.setString(6, customer.getBirthday());
            preparedStatement.setLong(7, customer.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return new JsonMessage("Error", "Customer update failed: "
                    + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonMessage("Error", "Customer update failed: "
                    + e.getMessage());
        }
        return new JsonMessage("Success", "Customer add succeeded...");
    }

    public JsonMessage delete(long id) {
        String deleteRowSQL = "DELETE FROM CUSTOMER WHERE ID=?";
        try (PreparedStatement preparedStatement = this.conn
                .prepareStatement(deleteRowSQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            return new JsonMessage("Error", "Customer delete failed: "
                    + e.getMessage());
        } catch (Exception e) {
            return new JsonMessage("Error", "Customer delete failed: "
                    + e.getMessage());
        }
        return new JsonMessage("Success", "Customer delete succeeded...");
    }

    public Customer[] findAll() {
        String queryStr = "SELECT * FROM CUSTOMER";
        return this.query(queryStr);
    }

    public Customer findbyId(long id) {
        String queryStr = "SELECT * FROM CUSTOMER WHERE ID=" + id;
        Customer customer = null;
        Customer customers[] = this.query(queryStr);
        if (customers != null && customers.length > 0) {
            customer = customers[0];
        }
        return customer;
    }

    public Customer findbyEmail(String email) {
        String queryStr = "SELECT * FROM CUSTOMER WHERE EMAIL='" + email+"'";
        Customer customer = null;
        Customer customers[] = this.query(queryStr);
        if (customers != null && customers.length > 0) {
            customer = customers[0];
        }
        return customer;
    }

    public Customer[] query(String sqlQueryStr) {
        ArrayList<Customer> cList = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQueryStr)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cList.add(new Customer(rs.getLong("ID"), rs
                        .getString("FIRSTNAME"), rs.getString("LASTNAME"), rs
                        .getString("EMAIL"), rs.getString("CITY"), rs
                        .getString("STATE"), rs.getString("BIRTHDAY")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cList.toArray(new Customer[0]);
    }

}
