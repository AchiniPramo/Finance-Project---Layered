package lk.ijse.Micro_Finance_Management_System.dao.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.LoanDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Loan;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        return false;
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
}
