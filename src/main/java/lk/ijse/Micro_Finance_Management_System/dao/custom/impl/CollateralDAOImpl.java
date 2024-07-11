package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.CollateralDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Collateral;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lk.ijse.Micro_Finance_Management_System.util.SQLUtil.sql;

public class CollateralDAOImpl implements CollateralDAO {
    @Override
    public ArrayList<Collateral> getAll() throws SQLException, ClassNotFoundException {
            ArrayList<Collateral> allCollateral = new ArrayList<>();
            ResultSet resultSet = SQLUtil.sql("SELECT * FROM Collateral");
            while (resultSet.next()) {
                Collateral collateral = new Collateral(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                allCollateral.add(collateral);
            }
            return allCollateral;
        }

    @Override
    public boolean save(Collateral entity) throws SQLException, ClassNotFoundException {
        return sql("INSERT INTO Collateral (Collateral_Id, Name, Address, Phone_Number) VALUES (?,?,?,?)",
                entity.getCollateralId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhoneNumber());

    }

    @Override
    public boolean update(Collateral entity) throws SQLException, ClassNotFoundException {
        return sql("UPDATE Collateral SET Name = ?, Address = ?, Phone_Number = ? WHERE Collateral_Id = ?",
                entity.getName(),
                entity.getAddress(),
                entity.getPhoneNumber(),
                entity.getCollateralId()
        );
    }

    @Override
    public Collateral search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = sql("SELECT  Collateral_Id, Name, Address, Phone_Number FROM Collateral WHERE Collateral_Id = ?", id);

        if (resultSet.next()) {
            return new Collateral(
                    resultSet.getString("Collateral_Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Phone_Number")
            );
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<String> getAllStringIds() throws SQLException {
        return null;
    }

    @Override
    public List<Integer> geAllIntegerIds() throws SQLException {
        return null;
    }

    @Override
    public boolean updateCollateralLoan(String collateralId, int loanId) throws SQLException {
        return SQLUtil.sql("INSERT INTO Collateral_Loan (Collateral_Id, Loan_Id) VALUES (?, ?)", collateralId, loanId);
    }
}
