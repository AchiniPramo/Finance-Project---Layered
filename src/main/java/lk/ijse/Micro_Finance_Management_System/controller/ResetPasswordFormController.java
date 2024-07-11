package lk.ijse.Micro_Finance_Management_System.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.Micro_Finance_Management_System.bo.BOFactory;
import lk.ijse.Micro_Finance_Management_System.bo.custom.UserBO;
import lk.ijse.Micro_Finance_Management_System.dto.UserDTO;
import lk.ijse.Micro_Finance_Management_System.util.GenarateCode;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.sql.SQLException;

public class ResetPasswordFormController {

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtOTPCode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    private String otpCode;

    private UserBO userBO = (UserBO) BOFactory.getBOFactory().getBOType(BOFactory.BOTypes.USER);

    @FXML
    void btnGetOTPOnAction(ActionEvent event) {
        // Generate the verification code
        otpCode = GenarateCode.genarateCode();

        boolean emailSent = userBO.sendVerificationCodeByEmail(otpCode);

        if (emailSent) {
            new Alert(Alert.AlertType.INFORMATION, "Verification code sent successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to send verification code. Please try again.").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        String enteredCode = txtOTPCode.getText();

        if (enteredCode.equals(otpCode)) {

            UserDTO userDTO = new UserDTO(txtUserId.getText(),txtUsername.getText(), txtPassword.getText());
            userBO.resetPassword(userDTO);
            clearFields();

            new Alert(Alert.AlertType.INFORMATION, "Password reset successful.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid verification code.").show();
        }
    }

    @FXML
    void linkGoBackLoginOnAction(ActionEvent event) {
        Navigation.navigateToLoginForm();
    }
    private void clearFields() {
        txtUserId.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtOTPCode.clear();
    }
}
