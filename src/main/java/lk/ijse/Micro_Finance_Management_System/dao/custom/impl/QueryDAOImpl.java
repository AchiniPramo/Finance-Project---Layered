package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.QueryDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Financial;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<Financial> loadAllDetails() throws SQLException {
        ArrayList<Financial> allDetails = new ArrayList<>();

        ResultSet resultSet = SQLUtil.sql("SELECT \n" +
                "    cl.Customer_Id AS CustomerId,\n" +
                "    c.Name AS CustomerName,\n" +
                "    cl.Loan_Id AS LoanId,\n" +
                "    l.Amount AS LoanAmount,\n" +
                "    coll.Name AS CollateralName,\n" +
                "    cl.Date_Issued AS IssueDate,\n" +
                "    cl.Date_Due AS DueDate,\n" +
                "    cl.Payment_Status AS LoanStatus,\n" +
                "    cl.Total_Amount_To_Pay AS TotalAmountToPay\n" +
                "FROM \n" +
                "    Customer_Loan cl\n" +
                "JOIN \n" +
                "    Customer c ON cl.Customer_Id = c.Customer_ID\n" +
                "JOIN \n" +
                "    Loan l ON cl.Loan_Id = l.Loan_Id\n" +
                "LEFT JOIN \n" +
                "    Collateral_Loan cln ON l.Loan_Id = cln.Loan_Id\n" +
                "LEFT JOIN \n" +
                "    Collateral coll ON cln.Collateral_Id = coll.Collateral_Id;\n");

        while (resultSet.next()) {
            allDetails.add(new Financial(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getString(8),
                    resultSet.getDouble(9)
            ));
        }
        return allDetails;
    }
}
