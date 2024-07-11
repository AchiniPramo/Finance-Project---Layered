package lk.ijse.Micro_Finance_Management_System.bo.custom.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.FinancialBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.QueryDAO;
import lk.ijse.Micro_Finance_Management_System.dto.FinancialDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Financial;

import java.sql.SQLException;
import java.util.ArrayList;

public class FinancialBOImpl implements FinancialBO {
    private QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.FINANCIAL);

    @Override
    public ArrayList<FinancialDTO> loadAllDetails() throws SQLException {
        ArrayList<FinancialDTO> allDetails = new ArrayList<>();
        ArrayList<Financial> all = queryDAO.loadAllDetails();

        for (Financial details : all) {
            allDetails.add(new FinancialDTO(
                    details.getCustomerId(),
                    details.getCustomerName(),
                    details.getLoanId(),
                    details.getAmount(),
                    details.getCollateralName(),
                    details.getIssue(),
                    details.getDue(),
                    details.getStatus(),
                    details.getTotalDue()
            ));
        }
        return allDetails;
    }
}
