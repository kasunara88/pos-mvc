/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.mvc.controller;

import java.util.ArrayList;
import pos.mvc.model.ItemModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import pos.mvc.db.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author kasun
 */
public class ItemController {
    
    public String saveItem(ItemModel itemModel) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO Item VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, itemModel.getItemCode());
        preparedStatement.setString(2, itemModel.getDescription());
        preparedStatement.setString(3, itemModel.getPacksize());
        preparedStatement.setDouble(4, itemModel.getUnitprice());
        preparedStatement.setInt(5, itemModel.getQtyOnHand());
        
         if (preparedStatement.executeUpdate() > 0) {
            return "Successfully Save";
        } else {
            return "Fail";
        }
        
    }

    public ArrayList<ItemModel> getAllItem() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT * FROM Item";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();

        ArrayList<ItemModel> itemModel = new ArrayList<>();

        while (result.next()) {
            ItemModel im = new ItemModel(result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5));
            itemModel.add(im);
        }
        return itemModel;
    }
}
