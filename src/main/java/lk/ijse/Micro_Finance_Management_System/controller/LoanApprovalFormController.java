package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.LoanBO;
import lk.ijse.Micro_Finance_Management_System.dto.CollateralDTO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.dto.LoanDTO;
import lk.ijse.Micro_Finance_Management_System.emailService.EmailService;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import java.sql.SQLException;

public class LoanApprovalFormController {

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtCollateralAddress;

    @FXML
    private TextField txtCollateralContact;

    @FXML
    private TextField txtCollateralId;

    @FXML
    private TextField txtCollateralName;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerContact;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtRate;
    private LoanBO loanBO = (LoanBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.LOAN);

    @FXML
    void btnCollateralAddOnAction(ActionEvent event) {
        Navigation.navigateToManageCollateral();
    }

    @FXML
    void btnCollateralUpdateOnAction(ActionEvent event) {
        Navigation.navigateToManageCollateral();
    }

    @FXML
    void btnCustomerAddOnAction(ActionEvent event) {
        Navigation.navigateToManageCustomerPage();
    }

    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {
        Navigation.navigateToManageCustomerPage();
    }

    @FXML
    void btnPlaceLoanOnAction(ActionEvent event) throws SQLException {
        String description = txtDescription.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        String duration = txtDuration.getText();
        double interestRate = Double.parseDouble(txtRate.getText());

        String customerId = txtCustomerId.getText();
        String collateralId = txtCollateralId.getText();

        if (isValid()) {
            try {
                LoanDTO loanDTO = new LoanDTO(0, description, amount, duration, interestRate);
                boolean isLoanSaved = loanBO.placeLoan(loanDTO, customerId, collateralId);
                if (isLoanSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Loan and Collateral Saved Successfully!").show();
                    sendEmailNotification(customerId, amount, interestRate);
                    clearLoanFields();
                    LoanFormController.getInstance().initialize();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save loan!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    private void sendEmailNotification(String customerId, double amount, double interestRate) {
        try {
            CustomerDTO customer = loanBO.getCustomer(customerId);
            if (customer != null) {
                String email = customer.getEmail();
                String subject = "Loan Approved";
                String body = "Dear " + customer.getName() + ",\n\n" +
                        "Your loan application has been approved.\n" +
                        "Loan Amount: $" + amount + "\n" +
                        "Interest Rate: " + interestRate + "%\n\n" +
                        "Thank you for choosing our services.\n" +
                        "Best regards,\n" +
                        "Micro Finance Management System";

                boolean emailSent = EmailService.sendEmail(email, subject, body);
                if (emailSent) {
                    System.out.println("Email sent successfully to " + email);
                } else {
                    System.out.println("Failed to send email to " + email);
                }
            } else {
                System.out.println("Customer not found for ID: " + customerId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtCollateralIdOnAction(ActionEvent event) {
        String collateralId = txtCollateralId.getText();
        try {
            CollateralDTO collateralDTO = loanBO.getCollateral(collateralId);
            if (collateralDTO != null) {
                fillCollateralFields(collateralDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Collateral Not Found!").show();
                clearCollateralFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearCollateralFields();
        }
    }

    @FXML
    void txtCustomerIdOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        try {
            CustomerDTO customerDTO = loanBO.getCustomer(customerId);
            if (customerDTO != null) {
                fillFields(customerDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            clearFields();
        }
    }

    private void fillFields(CustomerDTO customer) {
        txtCustomerId.setText(customer.getCustomerId());
        txtCustomerName.setText(customer.getName());
        txtCustomerAddress.setText(customer.getAddress());
        txtEmail.setText(customer.getEmail());
        txtCustomerContact.setText(customer.getMobileNo());
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtEmail.setText("");
        txtCustomerContact.setText("");
    }
    private void clearCollateralFields() {
        txtCollateralId.setText("");
        txtCollateralName.setText("");
        txtCollateralAddress.setText("");
        txtCollateralContact.setText("");
    }

    private void clearLoanFields() {
        txtDescription.setText("");
        txtDuration.setText("");
        txtAmount.setText("");
        txtRate.setText("");
    }

    private void fillCollateralFields(CollateralDTO collateral) {
        txtCollateralId.setText(String.valueOf(collateral.getCollateralId()));
        txtCollateralName.setText(String.valueOf(collateral.getName()));
        txtCollateralAddress.setText(String.valueOf(collateral.getAddress()));
        txtCollateralContact.setText(String.valueOf(collateral.getPhoneNumber()));
    }

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary,txtAmount);
    }

    @FXML
    void txtDescriptionOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtDescription);
    }

    @FXML
    void txtDurationOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Duration,txtDuration);
    }

    @FXML
    void txtInterestRateOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.InterestRate,txtRate);
    }

    public boolean isValid() {
        boolean isDescriptionValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtDescription);
        boolean isAmountValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary, txtAmount);
        boolean isDurationValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Duration, txtDuration);
        boolean isInterestRateValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.InterestRate, txtRate);
        boolean isCollateralIdValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id, txtCollateralId);
        boolean isCustomerIdValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id, txtCustomerId);

        return isDescriptionValid && isAmountValid && isDurationValid && isInterestRateValid && isCollateralIdValid && isCustomerIdValid;
    }
}

