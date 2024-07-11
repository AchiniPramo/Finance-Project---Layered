package lk.ijse.Micro_Finance_Management_System.dao.custom;

import lk.ijse.Micro_Finance_Management_System.dao.SuperDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Financial;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<Financial> loadAllDetails() throws SQLException;
}