package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.CustomerDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomer = new ArrayList<>();
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Customer");
        while (resultSet.next()) {
            Customer customer = new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            allCustomer.add(customer);
        }
        return allCustomer;
    }

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.sql("INSERT INTO Customer (Customer_Id, Name, Address, Email, Phone_Number) VALUES (?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getMobileNo()
        );
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("UPDATE Customer SET Name = ?, Address = ?, Email = ?, Phone_Number = ? WHERE Customer_Id = ?",
                entity.getName(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getMobileNo(),
                entity.getCustomerId()
        );
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql ("SELECT  Customer_Id, Name, Address, Email, Phone_Number FROM Customer WHERE Customer_Id = ?", id);

        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("DELETE FROM Customer WHERE Customer_Id = ?", id);
    }

    @Override
    public List<String> getAllStringIds() throws SQLException {
        return null;
    }

    @Override
    public List<Integer> geAllIntegerIds() throws SQLException {
        return null;
    }
}