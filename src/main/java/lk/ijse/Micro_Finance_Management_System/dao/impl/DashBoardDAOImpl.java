package lk.ijse.Micro_Finance_Management_System.dao.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.DashboardDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardDAOImpl implements DashboardDAO {
    @Override
    public int getCustomerCount() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Customer");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public int getEmployeeCount() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Employee");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public int getLoanCount() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Loan");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Object entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Object entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Object search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
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
