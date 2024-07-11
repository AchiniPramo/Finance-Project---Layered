package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.ExpenseBO;
import lk.ijse.Micro_Finance_Management_System.dto.ExpenseDTO;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;
import lk.ijse.Micro_Finance_Management_System.view.tm.ExpenseTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenseFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colExpenseId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<ExpenseTm> tblExpenses;

    private static ExpenseFormController controller;

    private ExpenseBO expenseBO = (ExpenseBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.EXPENSE);
    public ExpenseFormController() {
        controller=this;
    }

    public static ExpenseFormController getInstance(){
        return controller;
    }

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllExpenses();
    }

    private void loadAllExpenses() throws ClassNotFoundException {
        tblExpenses.getItems().clear();

        try {
            ArrayList<ExpenseDTO> allExpense = expenseBO.getAllExpense();

            for (ExpenseDTO expenseDTO : allExpense) {
                tblExpenses.getItems().add(new ExpenseTm(
                        expenseDTO.getExpenseId(),
                        expenseDTO.getType(),
                        expenseDTO.getDate(),
                        expenseDTO.getEmployeeId(),
                        expenseDTO.getAmount()
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colExpenseId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    public void btnManageExpenseOnAction(ActionEvent event) {
        Navigation.navigateToManageExpensePage();
    }
}
