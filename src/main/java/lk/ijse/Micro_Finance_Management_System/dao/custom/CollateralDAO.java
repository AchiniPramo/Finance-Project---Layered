package lk.ijse.Micro_Finance_Management_System.dao.custom;

import lk.ijse.Micro_Finance_Management_System.dao.CrudDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Collateral;

import java.sql.SQLException;

public interface CollateralDAO extends CrudDAO <Collateral> {
    boolean updateCollateralLoan(String collateralId, int loanId) throws SQLException;
}
