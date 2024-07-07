package lk.ijse.Micro_Finance_Management_System.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.UserBO;
import lk.ijse.Micro_Finance_Management_System.dto.UserDTO;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private UserBO userBO = (UserBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        try {
            UserDTO userDTO = new UserDTO(null,txtUsername.getText(),txtPassword.getText());
            userBO.checkCredentialAndLogin(userDTO);
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, "OOPS! Something went wrong").show();
        }
    }

    @FXML
    void linkResetOnAction(ActionEvent event) {
        Navigation.navigateToPasswordResetForm();
    }
}

