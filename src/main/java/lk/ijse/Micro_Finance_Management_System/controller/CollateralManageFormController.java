package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.CollateralBO;
import lk.ijse.Micro_Finance_Management_System.dto.CollateralDTO;
import lk.ijse.Micro_Finance_Management_System.util.Regex;

import java.sql.SQLException;

public class CollateralManageFormController {

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCollateralId;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    private CollateralBO collateralBO = (CollateralBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.COLLATERAL);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String collateralId = txtCollateralId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String phoneNumber = txtContact.getText();

        if (isValid()) {
            try {
                boolean isSaved = collateralBO.saveCollateral(new CollateralDTO(
                     collateralId,name,address,phoneNumber
                ));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Collateral Saved Successfully!").show();
                    clearFields();
                    CollateralFormController.getInstance().initialize();
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
        String collateralId = txtCollateralId.getText();
        try {
            CollateralDTO collateralDTO = collateralBO.searchCollateral(collateralId);

            if (collateralDTO != null) {
                fillFields(collateralDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Collateral Not Found!").show();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String collateralId = txtCollateralId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String phoneNumber = txtContact.getText();

        if (isValid()) {
        try {
            boolean isUpdated = collateralBO.updateCollateral(new CollateralDTO(
                    collateralId,name,address,phoneNumber
            ));
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Collateral Update Successfully!").show();
                clearFields();
                CollateralFormController.getInstance().initialize();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input Detected. Please check!").show();
        }
    }

    private void clearFields() {
        txtCollateralId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

    private void fillFields(CollateralDTO collateral) {
        txtCollateralId.setText(String.valueOf(collateral.getCollateralId()));
        txtName.setText(String.valueOf(collateral.getName()));
        txtAddress.setText(String.valueOf(collateral.getAddress()));
        txtContact.setText(String.valueOf(collateral.getPhoneNumber()));
    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address,txtAddress);
    }

    @FXML
    void txtCollateralIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id,txtCollateralId);
    }

    @FXML
    void txtCollateralNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name,txtName);
    }

    @FXML
    void txtContactNoOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact,txtContact);
    }

    public boolean isValid() {
        boolean isIdValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Id, txtCollateralId);
        boolean isNameValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Name, txtName);
        boolean isAddressValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Address, txtAddress);
        boolean isContactValid = Regex.setTextColor(lk.ijse.Micro_Finance_Management_System.util.TextField.Contact, txtContact);
        return isIdValid && isNameValid && isContactValid && isAddressValid;
    }
}

