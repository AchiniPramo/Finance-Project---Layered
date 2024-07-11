package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.CollateralDTO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.dto.LoanDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LoanBO extends SuperBO {
    ArrayList<LoanDTO> getAllLoan() throws SQLException, ClassNotFoundException;

    List<Integer> getAllLoanIds() throws SQLException, ClassNotFoundException;

    boolean placeLoan(LoanDTO loanDTO, String customerId, String collateralId)throws SQLException;

    CustomerDTO getCustomer(String customerId) throws SQLException, ClassNotFoundException;

    CollateralDTO getCollateral(String collateralId) throws SQLException, ClassNotFoundException;
}
