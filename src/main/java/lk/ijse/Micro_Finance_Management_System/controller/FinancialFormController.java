package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.FinancialBO;
import lk.ijse.Micro_Finance_Management_System.dto.FinancialDTO;
import lk.ijse.Micro_Finance_Management_System.view.tm.FinancialTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FinancialFormController {

    @FXML
    private Pane bodyPane;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCollateral;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDueDate;

    @FXML
    private TableColumn<?, ?> colIssueDate;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTotalDue;

    @FXML
    private TableView<FinancialTm> tblLoanDetail;

    private FinancialBO financialBO = (FinancialBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.FINANCIAL);

    @FXML
    void btnPlaceLoanOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/loan_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }

    public void initialize() {
        setCellValueFactory();
        loadAllDetails();
    }

    private void loadAllDetails() {
        tblLoanDetail.getItems().clear();

        try {
            ArrayList<FinancialDTO> loadAllDetails = financialBO.loadAllDetails();

            for (FinancialDTO allDetail : loadAllDetails) {
                tblLoanDetail.getItems().add(new FinancialTm(
                        allDetail.getCustomerId(),
                        allDetail.getCustomerName(),
                        allDetail.getLoanId(),
                        allDetail.getAmount(),
                        allDetail.getCollateralName(),
                        allDetail.getIssue(),
                        allDetail.getDue(),
                        allDetail.getStatus(),
                        allDetail.getTotalDue()
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new  PropertyValueFactory<>("customerName"));
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCollateral.setCellValueFactory(new PropertyValueFactory<>("collateralName"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issue"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("due"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotalDue.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
    }
}
