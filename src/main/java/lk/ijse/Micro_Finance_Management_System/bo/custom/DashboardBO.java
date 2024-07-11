package lk.ijse.Micro_Finance_Management_System.bo.custom;

import javafx.scene.chart.XYChart;
import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;

import java.sql.SQLException;

public interface DashboardBO extends SuperBO {

    int getCustomerCount() throws SQLException, ClassNotFoundException;

    int getEmployeeCount() throws SQLException, ClassNotFoundException;

    int getLoanCount() throws SQLException, ClassNotFoundException;

    XYChart.Series<String, Number> getBarChartData() throws SQLException;
}
