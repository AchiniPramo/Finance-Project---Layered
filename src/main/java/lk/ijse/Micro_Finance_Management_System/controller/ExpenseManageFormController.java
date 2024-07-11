package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.EmployeeBO;
import lk.ijse.Micro_Finance_Management_System.bo.custom.ExpenseBO;
import lk.ijse.Micro_Finance_Management_System.dto.ExpenseDTO;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ExpenseManageFormController {

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private ComboBox<Integer> cmbExpenseId;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtType;

    private ExpenseBO expenseBO = (ExpenseBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.EXPENSE);

    private EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.EMPLOYEE);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int expenseId = cmbExpenseId.getValue();
        try{
            ExpenseDTO expenseDTO = expenseBO.searchExpense(expenseId);
            if(expenseDTO != null) {
                boolean isDeleted = expenseBO.deleteExpense(expenseId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Expense Delete Successfully!").show();
                    clearFields();
                    ExpenseFormController.getInstance().initialize();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Expense Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String type = String.valueOf(txtType.getText());
        Date date = Date.valueOf(dtpDate.getValue());
        String employeeId = cmbEmployeeId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        if (isValid()) {
            try {
                boolean isSaved = expenseBO.saveExpense(new ExpenseDTO(
                        cmbExpenseId.getVisibleRowCount(),
                        type,
                        date,
                        employeeId,
                        amount
                ));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Expense Saved Successfully!").show();
                    clearFields();
                    ExpenseFormController.getInstance().initialize();
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
        int expenseId = cmbExpenseId.getValue();
        try {
            ExpenseDTO expenseDTO = expenseBO.searchExpense(expenseId);

            if (expenseDTO != null) {
                fillFields(expenseDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Expense Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String type = String.valueOf(txtType.getText());
        Date date = Date.valueOf(dtpDate.getValue());
        String employeeId = cmbEmployeeId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        if (isValid()){
        try {
            boolean isUpdated = expenseBO.updateExpense(new ExpenseDTO(
                    cmbExpenseId.getValue(), type,date,employeeId,amount
            ));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Expense Update Successfully!").show();
                clearFields();
                ExpenseFormController.getInstance().initialize();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    private void clearFields() {
        cmbExpenseId.getSelectionModel().clearSelection();
        txtType.setText("");
        dtpDate.setValue(null);
        cmbEmployeeId.getSelectionModel().clearSelection();
        txtAmount.setText("");
    }

    private void fillFields(ExpenseDTO expense) {
        cmbEmployeeId.setValue(expense.getEmployeeId());
        cmbExpenseId.setValue(expense.getExpenseId());
        txtType.setText(expense.getType());
        txtAmount.setText(String.valueOf(expense.getAmount()));
        dtpDate.setValue(expense.getDate().toLocalDate());
    }

    public void initialize() {
        loadAllEmployeeIds();
        loadAllExpenseIds();
    }

    private void loadAllExpenseIds() {
        try {
            List<Integer> expenseIds = expenseBO.getAllExpenseIds();
            cmbExpenseId.getItems().clear();
            cmbExpenseId.getItems().addAll(expenseIds);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllEmployeeIds() {
        try {
            List<String> employeeIds = employeeBO.getAllEmployeeIds();
            cmbEmployeeId.getItems().clear();
            cmbEmployeeId.getItems().addAll(employeeIds);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    }
}

    @FXML
    void txtAmountOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary,txtAmount);
    }

    @FXML
    void txtTypeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtType);
    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Date,dtpDate.getEditor());
    }
    public boolean isValid(){
        boolean isTypeValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtType);
        boolean isAmountValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Salary, txtAmount);
        boolean isDateValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Date, dtpDate.getEditor());

        return isTypeValid && isAmountValid && isDateValid;
    }
}
