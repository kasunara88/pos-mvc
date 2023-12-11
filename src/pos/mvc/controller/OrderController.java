/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.mvc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import pos.mvc.db.DBConnection;
import pos.mvc.model.OrderDetailModel;
import pos.mvc.model.OrderModel;

/**
 *
 * @author kasun
 */
public class OrderController {

    public String placeOrder(OrderModel orderModel, ArrayList<OrderDetailModel> orderDetilModels) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            String orderQuery = "INSERT INTO Orders VALUES (?,?,?)";
            PreparedStatement statementForOrder = connection.prepareStatement(orderQuery);
            statementForOrder.setString(1, orderModel.getOrderId());
            statementForOrder.setString(2, orderModel.getOrderDate());
            statementForOrder.setString(3, orderModel.getCustId());

            if (statementForOrder.executeUpdate() > 0) {
                boolean isOrderDetailSaved = true;
                String orderDetailQuery = "INSERT INTO OrderDetail VALUES (?,?,?,?)";
                PreparedStatement statementForOrderDetail = connection.prepareStatement(orderDetailQuery);

                for (OrderDetailModel oderDetailModel : orderDetilModels) {
                    statementForOrderDetail.setString(1, orderModel.getOrderId());
                    statementForOrderDetail.setString(2, oderDetailModel.getItemCode());
                    statementForOrderDetail.setInt(3, oderDetailModel.getOrderQty());
                    statementForOrderDetail.setInt(4, oderDetailModel.getDiscount());

                    if (!(statementForOrderDetail.executeUpdate() > 0)) {
                        isOrderDetailSaved = false;
                    }
                }

                if (isOrderDetailSaved) {
                    boolean isItemUpdate = true;
                    String itemQuery = "UPDATE Item SET QtyOnHand = QtyOnHand - ? WHERE ItemCode = ?";
                    PreparedStatement statementForItem = connection.prepareStatement(itemQuery);
                    for (OrderDetailModel oderDetailModel : orderDetilModels) {
                        statementForItem.setInt(1, oderDetailModel.getOrderQty());
                        statementForItem.setString(2, oderDetailModel.getItemCode());

                        if (!(statementForItem.executeUpdate() >= 0)) {
                            isItemUpdate = false;
                        }

                    }

                    if (isItemUpdate) {
                        connection.commit();
                        return "Success";
                    } else {
                        connection.rollback();
                        return "Item Save Error";
                    }

                } else {
                    connection.rollback();
                    return "Order Detail Save Error";
                }

            } else {
                connection.rollback();
                return "Order Save Error";
            }
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return e.getMessage();
        }finally {
        connection.setAutoCommit(true);
    }
    }

}
