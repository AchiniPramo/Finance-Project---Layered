package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.DashboardBO;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainBoardFormController {
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblLoanCount;

    private DashboardBO dashboardBO = (DashboardBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.DASHBOARD);

    @FXML
    private void initialize() throws SQLException {
        try {
            lblCustomerCount.setText(String.valueOf(getCustomerCount()));
            lblEmployeeCount.setText(String.valueOf(getEmployeeCount()));
            lblLoanCount.setText(String.valueOf(getLoanCount()));
            populateChart(barChart);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateChart(BarChart<String, Number> barChart) throws SQLException {
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
                "GROUP BY AmountRange;\n");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        while (resultSet.next()) {
            String amountRange = resultSet.getString("AmountRange");
            int count = resultSet.getInt("LoanCount");
            series.getData().add(new XYChart.Data<>(amountRange, count));
        }
        barChart.getData().add(series);
        for (Node n : barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill:  #F6F3D9;");
        }
    }

    private int getCustomerCount() {
        try {
            return dashboardBO.getCustomerCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return 0;
    }

    private int getEmployeeCount() {
        try {
            return dashboardBO.getEmployeeCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return 0;
    }

    private int getLoanCount() {
        try {
            return dashboardBO.getLoanCount();
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return 0;
    }
}
