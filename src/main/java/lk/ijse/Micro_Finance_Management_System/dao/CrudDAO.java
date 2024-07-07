package lk.ijse.Micro_Finance_Management_System.dao;

import lk.ijse.Micro_Finance_Management_System.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean save (T entity ) throws SQLException, ClassNotFoundException;

    boolean update(T entity) throws SQLException, ClassNotFoundException;

    T search(String id) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    List<String> getAllStringIds() throws SQLException;

    List<Integer> geAllIntegerIds() throws SQLException;
}
