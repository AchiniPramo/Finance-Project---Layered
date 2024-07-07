package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.ExpenseDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ExpenseBO extends SuperBO {
    ArrayList<ExpenseDTO> getAllExpense() throws SQLException, ClassNotFoundException;

    boolean saveExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException;

    boolean updateExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException;

    ExpenseDTO searchExpense(int expenseId) throws SQLException, ClassNotFoundException;

    boolean deleteExpense(int expenseId) throws SQLException, ClassNotFoundException;

    List<Integer> getAllExpenseIds()throws SQLException, ClassNotFoundException;
}
