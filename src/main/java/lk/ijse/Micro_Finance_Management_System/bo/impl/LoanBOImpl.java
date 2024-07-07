package lk.ijse.Micro_Finance_Management_System.bo.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.LoanBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.LoanDAO;
import lk.ijse.Micro_Finance_Management_System.dto.LoanDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Loan;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanBOImpl implements LoanBO {
    private LoanDAO loanDAO = (LoanDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.LOAN);
    @Override
    public ArrayList<LoanDTO> getAllLoan() throws SQLException, ClassNotFoundException {
        ArrayList<LoanDTO> allLoan = new ArrayList<>();
        ArrayList<Loan> all = loanDAO.getAll();

        for (Loan loan : all) {
            allLoan.add(new LoanDTO(
                    loan.getLoanId(),
                    loan.getDescription(),
                    loan.getAmount(),
                    loan.getDuration(),
                    loan.getInterestRate()
            ));
        }
        return allLoan;
    }

    @Override
    public List<Integer> getAllLoanIds() throws SQLException {
        return loanDAO.geAllIntegerIds();
    }
}
/*dadgjnnvvf*/