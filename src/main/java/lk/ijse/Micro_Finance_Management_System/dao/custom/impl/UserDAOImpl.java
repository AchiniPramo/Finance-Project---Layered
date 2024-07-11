package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.UserDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.entity.User;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User findUserByName(String userName) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM User WHERE Name = ?", userName);

        if (resultSet.next()) {
            return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        }
        return null;
    }

    @Override
    public void resetPassword(User entity) throws SQLException {
        SQLUtil.sql("UPDATE User SET Password = ? WHERE Name = ? AND User_ID = ?", entity.getPassword(), entity.getUserName(), entity.getUserId());
    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
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
