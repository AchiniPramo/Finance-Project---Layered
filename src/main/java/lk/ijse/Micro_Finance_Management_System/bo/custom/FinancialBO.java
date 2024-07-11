package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.FinancialDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FinancialBO extends SuperBO {
    ArrayList<FinancialDTO> loadAllDetails() throws SQLException;

}
