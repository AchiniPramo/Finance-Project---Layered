package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;

public interface UserBO extends SuperBO {

    void checkCredentialAndLogin(UserDTO userDTO)  throws SQLException, IOException;

    boolean sendVerificationCodeByEmail(String verificationCode);

    void resetPassword(UserDTO userDTO) throws SQLException;
}