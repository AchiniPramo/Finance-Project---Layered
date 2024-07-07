package lk.ijse.Micro_Finance_Management_System.dao.custom;

import lk.ijse.Micro_Finance_Management_System.dao.CrudDAO;
import lk.ijse.Micro_Finance_Management_System.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO <User> {
    User findUserByName(String userName) throws SQLException;

    void resetPassword(User entity) throws SQLException;
}
