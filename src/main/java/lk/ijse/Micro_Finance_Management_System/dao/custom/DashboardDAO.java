package lk.ijse.Micro_Finance_Management_System.dao.custom;

import lk.ijse.Micro_Finance_Management_System.dao.CrudDAO;

import java.sql.SQLException;

public interface DashboardDAO extends CrudDAO {
    int getCustomerCount() throws SQLException;

    int getEmployeeCount() throws SQLException;

    int getLoanCount() throws SQLException;
}
