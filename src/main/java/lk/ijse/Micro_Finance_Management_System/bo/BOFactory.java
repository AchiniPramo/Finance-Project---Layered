package lk.ijse.Micro_Finance_Management_System.bo;

import lk.ijse.Micro_Finance_Management_System.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    public enum BOTypes {
        USER, DASHBOARD, CUSTOMER, EMPLOYEE, LOAN, COLLATERAL, PAYMENT, EXPENSE, PENALTY, FINANCIAL
    }

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        return (boFactory==null) ? boFactory = new BOFactory():boFactory;
    }

    public SuperBO getBOType(BOTypes types) {
        switch (types) {
            case USER:
                return new UserBOImpl();

            case DASHBOARD:
                return new DashboardBOImpl();

            case CUSTOMER:
                return new CustomerBOImpl();

            case EMPLOYEE:
                return new EmployeeBOImpl();

            case LOAN:
                return new LoanBOImpl();

            case COLLATERAL:
                return new CollateralBOImpl();

            case PAYMENT:
                return new PaymentBOImpl();

            case EXPENSE:
                return new ExpenseBOImpl();

            case PENALTY:
                return new PenaltyBOImpl();

            case FINANCIAL:
                return new FinancialBOImpl();

            default:
                return null;
        }
    }
}
