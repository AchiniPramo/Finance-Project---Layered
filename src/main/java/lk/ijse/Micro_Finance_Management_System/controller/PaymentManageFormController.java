package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.LoanBO;
import lk.ijse.Micro_Finance_Management_System.bo.custom.PaymentBO;
import lk.ijse.Micro_Finance_Management_System.db.DbConnection;
import lk.ijse.Micro_Finance_Management_System.dto.PaymentDTO;
import lk.ijse.Micro_Finance_Management_System.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentManageFormController {

    @FXML
    private ComboBox<Integer> cmbLoanId;

    @FXML
    private ComboBox<Integer> cmbPaymentId;

    @FXML
    private DatePicker dtpPayment;

    @FXML
    private TextField txtAmount;

    private PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.PAYMENT);

    private LoanBO loanBO = (LoanBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.LOAN);

    @FXML
    void btnPrintBillOnAction(ActionEvent event) throws JRException, SQLException {
        if (cmbPaymentId.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a Payment ID to print the bill.").show();
            return;
        }

        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/Payment_Report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("PaymentId", cmbPaymentId.getValue());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                parameters,
                DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        Date date = Date.valueOf(dtpPayment.getValue());
        int loanId = cmbLoanId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        if (isValid()) {
            try {
                boolean isSaved = paymentBO.savePayment(new PaymentDTO(
                        cmbPaymentId.getVisibleRowCount(),date,loanId,amount
                ));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Payment Saved Successfully!").show();
                   clearFields();
                   PaymentFormController.getInstance().initialize();
                    initialize();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        if (cmbPaymentId.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a Payment ID to search.").show();
            return;
        }

        int paymentId = cmbPaymentId.getValue();
        try {
            PaymentDTO paymentDTO = paymentBO.searchByPaymentId(paymentId);

            if (paymentDTO != null) {
                fillFields(paymentDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Payment Id Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        cmbPaymentId.getSelectionModel().clearSelection();
        dtpPayment.setValue(null);
        cmbLoanId.getSelectionModel().clearSelection();
        txtAmount.setText("");
    }

    private void fillFields(PaymentDTO payment) {
        Date paymentDate = payment.getPaymentDate();
        if (paymentDate != null) {
            dtpPayment.setValue(paymentDate.toLocalDate());
        } else {
            dtpPayment.setValue(null);
        }
        cmbLoanId.setValue(payment.getLoanId());
        txtAmount.setText(String.valueOf(payment.getAmount()));
    }

    public void initialize() {
        loadAllPaymentIds();
        loadAllLoanIds();
    }

    private void loadAllLoanIds() {
        try {
            List<Integer> paymentIds = paymentBO.getAllPaymentIds();
            cmbPaymentId.getItems().clear();
            cmbPaymentId.getItems().addAll(paymentIds);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllPaymentIds() {
        try {
            List<Integer> loanIds = loanBO.getAllLoanIds();
            cmbLoanId.getItems().clear();
            cmbLoanId.getItems().addAll(loanIds);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void dtpPaymentDateOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Date, dtpPayment.getEditor());
    }

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary, txtAmount);
    }

    public boolean isValid() {
        boolean isPaymentDateValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Date, dtpPayment.getEditor());
        boolean isAmountValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary, txtAmount);

        return isPaymentDateValid && isAmountValid;
    }
}

