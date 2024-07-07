package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.CustomerBO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import java.sql.SQLException;

public class CustomerManageFormController {

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    private CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.CUSTOMER);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();

        try{
            CustomerDTO customerDTO = customerBO.searchCustomer(customerId);
            if(customerDTO != null) {
                boolean isDeleted = customerBO.deleteCustomer(customerId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete Successfully!").show();
                    clearFields();
                    CustomerFormController.getInstance().initialize();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtContactNo.getText();

        if (isValid()) {
        try {
            boolean isSaved = customerBO.saveCustomer(new CustomerDTO(
                    customerId,name,address,email,phoneNumber
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully!").show();
                clearFields();
                CustomerFormController.getInstance().initialize();
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
        String customerId = txtCustomerId.getText();

        try {
            CustomerDTO customerDTO = customerBO.searchCustomer(customerId);

            if (customerDTO != null) {
                fillFields(customerDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String mobileNo = txtContactNo.getText();

        if(isValid()) {
            try {
                boolean isUpdated = customerBO.updateCustomer(new CustomerDTO(
                        customerId,name,address,email,mobileNo
                ));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Update Successfully!").show();
                    clearFields();
                    CustomerFormController.getInstance().initialize();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            } else{
                new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
            }
    }

    private void fillFields(CustomerDTO customer) {
        txtCustomerId.setText(customer.getCustomerId());
        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
        txtEmail.setText(customer.getEmail());
        txtContactNo.setText(customer.getMobileNo());
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtContactNo.setText("");
    }
    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
    }

    @FXML
    void txtContactNoONKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact,txtContactNo);
    }

    @FXML
    void txtCustomerIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id,txtCustomerId);
    }

    @FXML
    void txtCustomerNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtName);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Email,txtEmail);
    }
    public boolean isValid() {
        boolean isIdValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id, txtCustomerId);
        boolean isNameValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtName);
        boolean isAddressValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
        boolean isEmailValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Email, txtEmail);
        boolean isContactValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact, txtContactNo);

        return isIdValid && isNameValid && isEmailValid && isContactValid && isAddressValid;
    }
}

