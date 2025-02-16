package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.EmployeeBO;
import lk.ijse.Micro_Finance_Management_System.dto.EmployeeDTO;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import java.sql.SQLException;

public class EmployeeManageFormController {

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    private EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.EMPLOYEE);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();

        try{
            EmployeeDTO employeeDTO = employeeBO.searchEmployee(employeeId);
            if(employeeDTO != null) {
                boolean isDeleted = employeeBO.deleteEmployee(employeeId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Delete Successfully!").show();
                    clearFields();
                    EmployeeFormController.getInstance().initialize();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String contactNumber = txtContactNo.getText();
        String email = txtEmail.getText();

        if (isValid()){
        try {
            boolean isSaved = employeeBO.saveEmployee(new EmployeeDTO(
                    employeeId,name,address,salary,contactNumber,email
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Successfully!").show();
                clearFields();
                EmployeeFormController.getInstance().initialize();
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
        String employeeId = txtEmployeeId.getText();

        try {
            EmployeeDTO employeeDTO = employeeBO.searchEmployee(employeeId);

            if (employeeDTO != null) {
                fillFields(employeeDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee Not Found!").show();
                clearFields();
                EmployeeFormController.getInstance().initialize();

            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String contactNumber = txtContactNo.getText();
        String email = txtEmail.getText();

        if (isValid()) {
            try {
                boolean isUpdated = employeeBO.updateEmployee(new EmployeeDTO(
                        employeeId,
                        name,
                        address,
                        salary,
                        contactNumber,
                        email
            ));
                if(isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Update Successfully!").show();
                    clearFields();
                    EmployeeFormController.getInstance().initialize();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    private void fillFields(EmployeeDTO employee) {
        txtEmployeeId.setText(employee.getEmployeeId());
        txtName.setText(employee.getName());
        txtAddress.setText(employee.getAddress());
        txtSalary.setText(String.valueOf(employee.getSalary()));
        txtContactNo.setText(employee.getPhoneNumber());
        txtEmail.setText(employee.getEmail());
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtSalary.setText("");
        txtContactNo.setText("");
        txtEmail.setText("");
    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
    }

    @FXML
    void txtContactNoOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact,txtContactNo);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Email,txtEmail);
    }

    @FXML
    void txtEmployeeIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id,txtEmployeeId);
    }

    @FXML
    void txtEmployeeNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtName);
    }

    @FXML
    void txtSalaryOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary,txtSalary);
    }

    public boolean isValid() {
        boolean isIdValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id, txtEmployeeId);
        boolean isNameValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtName);
        boolean isAddressValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
        boolean isContactValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact, txtContactNo);
        boolean isEmailValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Email, txtEmail);
        boolean isSalaryValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary, txtSalary);
        return isIdValid && isNameValid  && isContactValid && isEmailValid && isAddressValid && isSalaryValid;
    }
}
