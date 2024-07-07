package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.PenaltyDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PenaltyBO extends SuperBO {
    ArrayList<PenaltyDTO> getAllPenalty() throws SQLException, ClassNotFoundException;
}
