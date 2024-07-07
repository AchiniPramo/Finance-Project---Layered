package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.PaymentBO;
import lk.ijse.Micro_Finance_Management_System.dto.PaymentDTO;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;
import lk.ijse.Micro_Finance_Management_System.view.tm.PaymentTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colLoanId;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableView<PaymentTm> tblPayment;

    private static PaymentFormController controller;

    private PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.PAYMENT);
    public PaymentFormController(){

        controller=this;
    }
    public static PaymentFormController getInstance(){

        return controller;
    }

    @FXML
    void btnManagePaymentOnAction(ActionEvent event) {
        Navigation.navigateToManagePaymentPage();
    }

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllPayment();
    }

    private void loadAllPayment() throws ClassNotFoundException {
        tblPayment.getItems().clear();

        try {
            ArrayList<PaymentDTO> allPayment = paymentBO.getAllPayment();

            for (PaymentDTO paymentDTO : allPayment) {
                tblPayment.getItems().add(new PaymentTm(
                        paymentDTO.getPaymentId(),
                        paymentDTO.getPaymentDate(),
                        paymentDTO.getLoanId(),
                        paymentDTO.getAmount()

                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
}
