package lk.ijse.Micro_Finance_Management_System.bo.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.PenaltyBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.PenaltyDAO;
import lk.ijse.Micro_Finance_Management_System.dto.ExpenseDTO;
import lk.ijse.Micro_Finance_Management_System.dto.PenaltyDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Expense;
import lk.ijse.Micro_Finance_Management_System.entity.Penalty;

import java.sql.SQLException;
import java.util.ArrayList;

public class PenaltyBOImpl implements PenaltyBO {
    private PenaltyDAO penaltyDAO = (PenaltyDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.PENALTY);
    @Override
    public ArrayList<PenaltyDTO> getAllPenalty() throws SQLException, ClassNotFoundException {
        ArrayList<PenaltyDTO> allPenalty = new ArrayList<>();
        ArrayList<Penalty> all = penaltyDAO.getAll();

        for (Penalty penalty : all) {
            allPenalty.add(new PenaltyDTO(
                    penalty.getPenaltyId(),
                    penalty.getReason(),
                    penalty.getLoanId(),
                    penalty.getAmount(),
                    penalty.getDateApplied()
            ));
        }
        return allPenalty;
    }
}

