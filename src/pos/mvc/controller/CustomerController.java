/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.mvc.controller;

import pos.mvc.model.CustomerModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import pos.mvc.db.DBConnection;

/**
 *
 * @author kasun
 */
public class CustomerController {

    public String saveCustomer(CustomerModel customer) throws SQLException {
        Connection connect = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO Customer VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, customer.getCustID());
        preparedStatement.setString(2, customer.getTitle());
        preparedStatement.setString(3, customer.getName());
        preparedStatement.setString(4, customer.getDob());
        preparedStatement.setDouble(5, customer.getSalary());
        preparedStatement.setString(6, customer.getAddress());
        preparedStatement.setString(7, customer.getCity());
        preparedStatement.setString(8, customer.getProvince());
        preparedStatement.setString(9, customer.getZip());

        if (preparedStatement.executeUpdate() > 0) {
            return "Successfully Save";
        } else {
            return "Fail";
        }
    }

    public ArrayList<CustomerModel> getAllCustomers() throws SQLException {
        Connection connect = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM Customer";
        PreparedStatement statment = connect.prepareStatement(query);
        ResultSet result = statment.executeQuery();

        ArrayList<CustomerModel> customerModel = new ArrayList<>();

        while (result.next()) {
            CustomerModel cm = new CustomerModel(result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getDouble(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9));
            customerModel.add(cm);
        }
        return customerModel;
    }

    public CustomerModel getCustomer(String custId) throws SQLException {
        Connection connect = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM Customer WHERE CustID=?";
        PreparedStatement statment = connect.prepareStatement(query);
        statment.setString(1, custId);

        ResultSet result = statment.executeQuery();

        while (result.next()) {
            CustomerModel cm = new CustomerModel(result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getDouble(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9));
            return cm;
        }
        return null;
    }

    public String updateCustomer(CustomerModel customer) throws SQLException {
        Connection connect = DBConnection.getInstance().getConnection();
        String query = "UPDATE Customer SET CustTitle=?,CustName =?,DOB =?,salary =?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=?";
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setString(1, customer.getTitle());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getDob());
        preparedStatement.setDouble(4, customer.getSalary());
        preparedStatement.setString(5, customer.getAddress());
        preparedStatement.setString(6, customer.getCity());
        preparedStatement.setString(7, customer.getProvince());
        preparedStatement.setString(8, customer.getZip());
        preparedStatement.setString(9, customer.getCustID());

        if (preparedStatement.executeUpdate() > 0) {
            return "Successfully Update";
        } else {
            return "Update Fail";
        }
    }

    public String deleteCustomer(String CustID) throws SQLException {
        Connection connect = DBConnection.getInstance().getConnection();
        String query = "DELETE FROM Customer WHERE CustID=?";
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, CustID);
        
         if (preparedStatement.executeUpdate() > 0) {
            return "Successfully Delete";
        } else {
            return "Delete Fail";
        }
    }
}
