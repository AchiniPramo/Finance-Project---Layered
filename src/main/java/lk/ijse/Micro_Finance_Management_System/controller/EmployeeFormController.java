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
import lk.ijse.Micro_Finance_Management_System.bo.custom.EmployeeBO;
import lk.ijse.Micro_Finance_Management_System.dto.EmployeeDTO;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;
import lk.ijse.Micro_Finance_Management_System.view.tm.EmployeeTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeFormController {
    @FXML
    private Pane bodyPane;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    private static EmployeeFormController controller;
    public EmployeeFormController(){
        controller=this;
    }
    public static EmployeeFormController getInstance(){
        return controller;
    }
    private EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.EMPLOYEE);

    @FXML
    void btnEmployeeManageOnAction(ActionEvent event) {
        Navigation.navigateToManageEmployeePage();
    }

    @FXML
    void btnExpenseOnAction(ActionEvent event) {
        bodyPane.getChildren().clear();
        try {
            bodyPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/expense_form.fxml")));
        } catch (IOException e) {
            showErrorAlert();
        }
    }

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllEmployees();
    }

    public void loadAllEmployees() throws ClassNotFoundException {
        tblEmployee.getItems().clear();

        try {
            ArrayList<EmployeeDTO> allEmployee = employeeBO.getAllEmployee();

            for (EmployeeDTO employeeDTO : allEmployee) {
                tblEmployee.getItems().add(new EmployeeTm(
                        employeeDTO.getEmployeeId(),
                        employeeDTO.getName(),
                        employeeDTO.getAddress(),
                        employeeDTO.getSalary(),
                        employeeDTO.getPhoneNumber(),
                        employeeDTO.getEmail()
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Navigation Error");
        alert.showAndWait();
    }
}
