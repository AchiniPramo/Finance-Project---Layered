package lk.ijse.Micro_Finance_Management_System.bo.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.ExpenseBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.ExpenseDAO;
import lk.ijse.Micro_Finance_Management_System.dto.ExpenseDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Expense;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseBOImpl implements ExpenseBO {
    private ExpenseDAO expenseDAO = (ExpenseDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.EXPENSE);
    @Override
    public ArrayList<ExpenseDTO> getAllExpense() throws SQLException, ClassNotFoundException {
        ArrayList<ExpenseDTO> allExpense = new ArrayList<>();
        ArrayList<Expense> all = expenseDAO.getAll();

        for (Expense expense : all) {
            allExpense.add(new ExpenseDTO(
                    expense.getExpenseId(),
                    expense.getType(),
                    expense.getDate(),
                    expense.getEmployeeId(),
                    expense.getAmount()

            ));
        }
        return allExpense;
    }

    @Override
    public boolean saveExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException {
        return expenseDAO.save(new Expense(
                expenseDTO.getExpenseId(),
                expenseDTO.getType(),
                expenseDTO.getDate(),
                expenseDTO.getEmployeeId(),
                expenseDTO.getAmount()
        ));
    }

    @Override
    public boolean updateExpense(ExpenseDTO expenseDTO) throws SQLException, ClassNotFoundException {
        return expenseDAO.update(new Expense(
                expenseDTO.getExpenseId(),
                expenseDTO.getType(),
                expenseDTO.getDate(),
                expenseDTO.getEmployeeId(),
                expenseDTO.getAmount()
        ));
    }

    @Override
    public ExpenseDTO searchExpense(int expenseId) throws SQLException, ClassNotFoundException {
        Expense expense = expenseDAO.search(String.valueOf(expenseId));
        if (expense != null){
            return new ExpenseDTO(
                    expense.getExpenseId(),
                    expense.getType(),
                    expense.getDate(),
                    expense.getEmployeeId(),
                    expense.getAmount()
            );
        }
        return null;
    }

    @Override
    public boolean deleteExpense(int expenseId) throws SQLException, ClassNotFoundException {
        return expenseDAO.delete(String.valueOf(expenseId));
    }

    @Override
    public List<Integer> getAllExpenseIds() throws SQLException, ClassNotFoundException {
        return expenseDAO.geAllIntegerIds();
    }
}
