package lk.ijse.Micro_Finance_Management_System.bo.custom.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.CollateralBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.CollateralDAO;
import lk.ijse.Micro_Finance_Management_System.dto.CollateralDTO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Collateral;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CollateralBOImpl implements CollateralBO {

    private CollateralDAO collateralDAO = (CollateralDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.COLLATERAL);
    @Override
    public ArrayList<CollateralDTO> getAllCollateral() throws SQLException, ClassNotFoundException {
        ArrayList<CollateralDTO> allCollateral = new ArrayList<>();
        ArrayList<Collateral> all = collateralDAO.getAll();

        for (Collateral collateral : all) {
            allCollateral.add(new CollateralDTO(
                    collateral.getCollateralId(),
                    collateral.getName(),
                    collateral.getAddress(),
                    collateral.getPhoneNumber()

            ));
        }
        return allCollateral;
    }

    @Override
    public boolean saveCollateral(CollateralDTO collateralDTO) throws SQLException, ClassNotFoundException {
        return collateralDAO.save(new Collateral(
                collateralDTO.getCollateralId(),
                collateralDTO.getName(),
                collateralDTO.getAddress(),
                collateralDTO.getPhoneNumber()
        ));
    }

    @Override
    public boolean updateCollateral(CollateralDTO collateralDTO) throws SQLException, ClassNotFoundException {
        return collateralDAO.update(new Collateral(
                collateralDTO.getCollateralId(),
                collateralDTO.getName(),
                collateralDTO.getAddress(),
                collateralDTO.getPhoneNumber()
        ));
    }

    @Override
    public CollateralDTO searchCollateral(String collateralId) throws SQLException, ClassNotFoundException {
        Collateral collateral = collateralDAO.search(collateralId);
        if (collateral != null){
            return new CollateralDTO(
                    collateral.getCollateralId(),
                    collateral.getName(),
                    collateral.getAddress(),
                    collateral.getPhoneNumber()
            );
        }
        return null;
    }
}