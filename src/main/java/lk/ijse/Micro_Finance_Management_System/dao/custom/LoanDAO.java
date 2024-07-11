package lk.ijse.Micro_Finance_Management_System.dao.custom;

import lk.ijse.Micro_Finance_Management_System.dao.CrudDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Loan;

import java.sql.SQLException;

public interface LoanDAO extends CrudDAO <Loan> {
    int getInsertedLoanId() throws SQLException;

    double calculateTotalAmountToPay(double amount, double interestRate, int i);

    boolean checkIfOverdue(int loanId) throws SQLException;

    double calculatePenaltyAmount(double totalAmountToPay, int loanId)throws SQLException ;

    void addPenaltyIfOverdue(int loanId, double penaltyAmount) throws SQLException;

    boolean insertCustomerLoan(int loanId, String customerId, String formattedCurrentDate, String formattedDueDate, double totalAmountToPay)throws SQLException;
}
