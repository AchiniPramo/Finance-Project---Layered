package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.DashboardBO;

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
    private void initialize() {
        lblCustomerCount.setText(String.valueOf(getCustomerCount()));
        lblEmployeeCount.setText(String.valueOf(getEmployeeCount()));
        lblLoanCount.setText(String.valueOf(getLoanCount()));
        populateChart(barChart);
    }

    private void populateChart(BarChart<String, Number> barChart){
        XYChart.Series<String, Number> series = null;

        try {
            series = dashboardBO.getBarChartData();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        barChart.getData().add(series);

        for (Node node:barChart.lookupAll(".default-color0.chart-bar")) {
            node.setStyle("-fx-bar-fill:  #F6F3D9;");
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
