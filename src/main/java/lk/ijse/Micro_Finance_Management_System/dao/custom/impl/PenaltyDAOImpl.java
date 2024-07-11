package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.PenaltyDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Penalty;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenaltyDAOImpl implements PenaltyDAO {
    @Override
    public ArrayList<Penalty> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Penalty> allPenalty = new ArrayList<>();
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Penalty");
        while (resultSet.next()) {
            Penalty penalty = new Penalty(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDate(5).toLocalDate()
            );
            allPenalty.add(penalty);
        }
        return allPenalty;
    }

    @Override
    public boolean save(Penalty entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Penalty entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Penalty search(String id) throws SQLException, ClassNotFoundException {
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
}

