package lk.ijse.Micro_Finance_Management_System.dao;

import lk.ijse.Micro_Finance_Management_System.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public enum DAOTypes {
        USER, DASHBOARD, CUSTOMER, EMPLOYEE, LOAN, COLLATERAL, PAYMENT, EXPENSE, PENALTY ,FINANCIAL
    }

    private DAOFactory() {
    }

    public static DAOFactory getDAOFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAOType(DAOTypes types) {
        switch (types) {
            case USER:
                return new UserDAOImpl();

            case DASHBOARD:
                return new DashBoardDAOImpl();

            case CUSTOMER:
                return new CustomerDAOImpl();

            case EMPLOYEE:
                return new EmployeeDAOImpl();

            case LOAN:
                return new LoanDAOImpl();

            case COLLATERAL:
                return new CollateralDAOImpl();

            case PAYMENT:
                return new PaymentDAOImpl();

            case EXPENSE:
                return new ExpenseDAOImpl();

            case PENALTY:
                return new PenaltyDAOImpl();

            case FINANCIAL:
                return new QueryDAOImpl();

            default:
                return null;
        }
    }
}
