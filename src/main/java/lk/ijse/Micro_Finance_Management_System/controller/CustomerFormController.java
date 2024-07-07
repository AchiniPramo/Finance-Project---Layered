package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.CustomerBO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;
import lk.ijse.Micro_Finance_Management_System.view.tm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView <CustomerTm> tblCustomer;
    private static CustomerFormController controller;

    private CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.CUSTOMER);
    public CustomerFormController(){

        controller=this;
    }
    public static CustomerFormController getInstance(){

        return controller;
    }

    @FXML
    void btnCustomerManageOnAction(ActionEvent event) {
        Navigation.navigateToManageCustomerPage();
    }

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
    }

  private void loadAllCustomer() {
        tblCustomer.getItems().clear();

        try {
            ArrayList<CustomerDTO> allCustomer = customerBO.getAllCustomer();

            for (CustomerDTO customerDTO : allCustomer) {
                tblCustomer.getItems().add(new CustomerTm(
                        customerDTO.getCustomerId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getEmail(),
                        customerDTO.getMobileNo()
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
}

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
    }
}
