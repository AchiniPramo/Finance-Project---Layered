package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import javafx.scene.chart.XYChart;
import lk.ijse.Micro_Finance_Management_System.dao.custom.DashboardDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardDAOImpl implements DashboardDAO {
    @Override
    public int getCustomerCount() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Customer");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public int getEmployeeCount() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Employee");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public int getLoanCount() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM Loan");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    @Override
    public XYChart.Series<String, Number> getBarChartData() throws SQLException {
        ResultSet resultSet = SQLUtil.sql("SELECT\n" +
                "    CASE\n" +
                "        WHEN Amount < 500 THEN 'Less than 500'\n" +
                "        WHEN Amount BETWEEN 500 AND 999.99 THEN '500-999.99'\n" +
                "        WHEN Amount BETWEEN 1000 AND 4999.99 THEN '1000-4999.99'\n" +
                "        WHEN Amount BETWEEN 5000 AND 9999.99 THEN '5000-9999.99'\n" +
                "        ELSE '10000 and above'\n" +
                "    END AS AmountRange,\n" +
                "    COUNT(*) AS LoanCount\n" +
                "FROM Loan\n" +
                "GROUP BY AmountRange;");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        while (resultSet.next()) {
            String propertyType = resultSet.getString("AmountRange");
            int count = resultSet.getInt("LoanCount");
            series.getData().add(new XYChart.Data<>(propertyType, count));
        }
        return series;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Object entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Object entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Object search(String id) throws SQLException, ClassNotFoundException {
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
