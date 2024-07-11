package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.LoanDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Loan;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanDAOImpl implements LoanDAO {

    @Override
    public ArrayList<Loan> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Loan> allLoan = new ArrayList<>();
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Loan");
        while (resultSet.next()) {
            Loan loan = new Loan(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            allLoan.add(loan);
        }
        return allLoan;
    }

    @Override
    public boolean save(Loan entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO Loan (Description, Amount, Duration, Interest_Rate) VALUES (?, ?, ?, ?)", entity.getDescription(), entity.getAmount(), entity.getDuration(), entity.getInterestRate());
    }

    @Override
    public boolean update(Loan entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Loan search(String id) throws SQLException, ClassNotFoundException {
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
        List<Integer> loanIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Loan_Id FROM Loan")) {
            while (resultSet.next()) {
                Integer loanId = resultSet.getInt("Loan_Id");
                loanIds.add(loanId);
            }
        }
        return loanIds;
    }

    @Override
    public int getInsertedLoanId() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT LAST_INSERT_ID()");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    @Override
    public double calculateTotalAmountToPay(double amount, double interestRate, int durationInDays) {
        double monthlyInterest = interestRate / 100.0;
        return amount * (1 + (monthlyInterest * (durationInDays / 30.0)));
    }

    @Override
    public boolean checkIfOverdue(int loanId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT Date_Due FROM Customer_Loan WHERE Loan_Id = ?", loanId);
        if (resultSet.next()) {
            LocalDate dueDate = resultSet.getDate("Date_Due").toLocalDate();
            return LocalDate.now().isAfter(dueDate);
        }
        return false;
    }

    @Override
    public double calculatePenaltyAmount(double totalAmountToPay, int loanId) throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT Date_Due FROM Customer_Loan WHERE Loan_Id = ?", loanId);
        if (resultSet.next()) {
            LocalDate dueDate = resultSet.getDate("Date_Due").toLocalDate();
            long daysDifference = LocalDate.now().toEpochDay() - dueDate.toEpochDay();
            double penaltyRate = 0.0025; // 0.25%
            double penaltyAmount = totalAmountToPay * penaltyRate * daysDifference;
            return penaltyAmount;
        }
        return 0.0;
    }


    @Override
    public void addPenaltyIfOverdue(int loanId, double penaltyAmount) throws SQLException {
        String reason = "Overdue Penalty";
        LocalDate currentDate = LocalDate.now();
        String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Insert into Penalty table
        boolean isPenaltyInserted = insertPenalty(reason, loanId, penaltyAmount, formattedCurrentDate);
        if (isPenaltyInserted) {
            System.out.println("Penalty added successfully for Loan ID: " + loanId);
        } else {
            System.out.println("Failed to add penalty for Loan ID: " + loanId);
        }
    }

    private boolean insertPenalty(String reason, int loanId, double amount, String dateApplied) throws SQLException {
        return SQLUtil.sql("INSERT INTO Penalty (Reason, Loan_Id, Amount, Date_Applied) VALUES (?, ?, ?, ?)", reason, loanId, amount, dateApplied);
    }

    @Override
    public boolean insertCustomerLoan(int loanId, String customerId, String formattedCurrentDate, String formattedDueDate, double totalAmountToPay) throws SQLException {
        return SQLUtil.sql("INSERT INTO Customer_Loan (Loan_Id, Customer_Id, Date_Issued, Date_Due, Payment_Status, Total_Amount_To_Pay) VALUES (?, ?, ?, ?, 'Placed', ?)", loanId, customerId, formattedCurrentDate, formattedDueDate, totalAmountToPay);
    }
}