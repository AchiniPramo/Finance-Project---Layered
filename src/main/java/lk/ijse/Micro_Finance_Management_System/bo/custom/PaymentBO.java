package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PaymentBO extends SuperBO {
    ArrayList<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException;

    boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;

    PaymentDTO searchByPaymentId(int paymentId) throws SQLException, ClassNotFoundException;

    List<Integer> getAllPaymentIds() throws SQLException, ClassNotFoundException;

}
