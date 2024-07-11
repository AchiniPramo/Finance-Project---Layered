package lk.ijse.Micro_Finance_Management_System.bo.custom.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.PaymentBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.PaymentDAO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.dto.PaymentDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    private PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public ArrayList<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDTO> allPayment = new ArrayList<>();
        ArrayList<Payment> all = paymentDAO.getAll();

        for (Payment payment : all) {
            allPayment.add(new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getPaymentDate(),
                    payment.getLoanId(),
                    payment.getAmount()
            ));
        }
        return allPayment;
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(
                paymentDTO.getPaymentId(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getLoanId(),
                paymentDTO.getAmount()
        ));
    }

    @Override
    public PaymentDTO searchByPaymentId(int paymentId) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.search(String.valueOf(paymentId));
        if (payment != null){
            return new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getPaymentDate(),
                    payment.getLoanId(),
                    payment.getAmount()
            );
        }
        return null;
    }

    @Override
    public List<Integer> getAllPaymentIds() throws SQLException {
        return paymentDAO.geAllIntegerIds();
    }

}
