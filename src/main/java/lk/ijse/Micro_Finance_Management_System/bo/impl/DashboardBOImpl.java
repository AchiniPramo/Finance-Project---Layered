package lk.ijse.Micro_Finance_Management_System.bo.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.DashboardBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.DashboardDAO;

import java.sql.SQLException;

public class DashboardBOImpl implements DashboardBO {
    private DashboardDAO dashboardDAO = (DashboardDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.DASHBOARD);
    @Override
    public int getCustomerCount() throws SQLException {
        return dashboardDAO.getCustomerCount();
    }

    @Override
    public int getEmployeeCount() throws SQLException, ClassNotFoundException {
        return dashboardDAO.getEmployeeCount();
    }

    @Override
    public int getLoanCount() throws SQLException,  ClassNotFoundException {
        return dashboardDAO.getLoanCount();
    }
}
