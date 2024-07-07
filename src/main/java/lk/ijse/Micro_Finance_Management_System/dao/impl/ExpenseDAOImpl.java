package lk.ijse.Micro_Finance_Management_System.dao.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.ExpenseDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Expense;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOImpl implements ExpenseDAO {
    @Override
    public ArrayList<Expense> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Expense> allExpense = new ArrayList<>();
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Expense");
        while (resultSet.next()) {
            Expense expense = new Expense(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            allExpense.add(expense);
        }
        return allExpense;
    }

    @Override
    public boolean save(Expense entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO Expense (Type, Date, Employee_Id, Amount) VALUES (?,?,?,?)",
                entity.getType(),
                entity.getDate(),
                entity.getEmployeeId(),
                entity.getAmount());
    }

    @Override
    public boolean update(Expense entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("UPDATE Expense SET Type = ?, Date = ?, Employee_Id = ?, Amount = ? WHERE Expense_Id = ?",
                entity.getType(),
                entity.getDate(),
                entity.getEmployeeId(),
                entity.getAmount(),
                entity.getExpenseId());
    }

    @Override
    public Expense search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Expense WHERE Expense_Id = ?", id);

        if (resultSet.next()) {
            return new Expense(
                    resultSet.getInt("Expense_Id"),
                    resultSet.getString("Type"),
                    resultSet.getDate("Date"),
                    resultSet.getString("Employee_Id"),
                    resultSet.getDouble("Amount")
            );
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("DELETE FROM Expense WHERE Expense_Id = ?", id);
    }

    @Override
    public List<String> getAllStringIds() throws SQLException {
        return null;
    }

    @Override
    public List<Integer> geAllIntegerIds() throws SQLException {
        List<Integer> expenseIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Expense_Id FROM Expense")) {
            while (resultSet.next()) {
                int expenseId = resultSet.getInt("Expense_Id");
                expenseIds.add(expenseId);
            }
        }
        return expenseIds;
    }
}
