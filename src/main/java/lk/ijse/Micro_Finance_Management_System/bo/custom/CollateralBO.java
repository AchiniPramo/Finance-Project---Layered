package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.CollateralDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CollateralBO extends SuperBO {
    ArrayList<CollateralDTO> getAllCollateral() throws SQLException, ClassNotFoundException;

    boolean saveCollateral(CollateralDTO collateralDTO) throws SQLException, ClassNotFoundException;

    boolean updateCollateral(CollateralDTO collateralDTO) throws SQLException, ClassNotFoundException;

    CollateralDTO searchCollateral(String collateralId) throws SQLException, ClassNotFoundException;
}
