package lk.ijse.Micro_Finance_Management_System.bo.impl;

import javafx.scene.control.Alert;
import lk.ijse.Micro_Finance_Management_System.bo.custom.UserBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.UserDAO;
import lk.ijse.Micro_Finance_Management_System.dto.UserDTO;
import lk.ijse.Micro_Finance_Management_System.emailService.EmailService;
import lk.ijse.Micro_Finance_Management_System.entity.User;
import lk.ijse.Micro_Finance_Management_System.util.Navigation;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {
    private UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.USER);
    @Override
    public void checkCredentialAndLogin(UserDTO userDTO) throws SQLException {
        User user = new User(null, userDTO.getUserName(), userDTO.getPassword());
        User dbUser = userDAO.findUserByName(user.getUserName());

        if (dbUser != null) {
            if (dbUser.getPassword().equals(user.getPassword())) {
                Navigation.navigateToDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "Password is incorrect!").show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "User ID not found!").show();
        }
    }

    @Override
    public boolean sendVerificationCodeByEmail(String verificationCode) {
        return EmailService.sendCodeByEmail(verificationCode);
    }

    @Override
    public void resetPassword(UserDTO userDTO) throws SQLException {
        User user = new User(userDTO.getUserId(), userDTO.getUserName(), userDTO.getPassword());
        userDAO.resetPassword(user);
    }
}
