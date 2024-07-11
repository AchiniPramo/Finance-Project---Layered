package lk.ijse.Micro_Finance_Management_System.dao.custom.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.PaymentDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Payment;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;
import lk.ijse.Micro_Finance_Management_System.util.TransactionUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> allPayment = new ArrayList<>();
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Payment");
        while (resultSet.next()) {
            Payment payment = new Payment(
                    resultSet.getInt(1),
                    resultSet.getDate(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            );
            allPayment.add(payment);
        }
        return allPayment;
    }

    @Override
    public boolean save(Payment entity) throws SQLException, ClassNotFoundException {
        try {
            TransactionUtil.startTransaction(); // Start transaction

            // Insert Payment table
            String insertPaymentSQL = "INSERT INTO Payment (Payment_Date, Loan_Id, Amount) VALUES (?, ?, ?)";
            boolean isPaymentInserted = SQLUtil.<Boolean>sql(insertPaymentSQL, entity.getPaymentDate(), entity.getLoanId(), entity.getAmount());

            // Update Total_Amount_To_Pay in Customer_Loan table
            String updateCustomerLoanSQL = "UPDATE Customer_Loan SET Total_Amount_To_Pay = Total_Amount_To_Pay - ? WHERE Loan_Id = ?";
            boolean isCustomerLoanUpdated = SQLUtil.<Boolean>sql(updateCustomerLoanSQL, entity.getAmount(), entity.getLoanId());

            String checkAmountSQL = "SELECT Total_Amount_To_Pay FROM Customer_Loan WHERE Loan_Id = ?";
            ResultSet resultSet = SQLUtil.sql(checkAmountSQL, entity.getLoanId());
            if (resultSet.next() && resultSet.getDouble("Total_Amount_To_Pay") == 0) {
                String updateStatusSQL = "UPDATE Customer_Loan SET Payment_Status = 'Loan Closed' WHERE Loan_Id = ?";
                boolean isStatusUpdated = SQLUtil.<Boolean>sql(updateStatusSQL, entity.getLoanId());
                if (!isStatusUpdated) {
                    TransactionUtil.rollBack();
                    return false;
                }
            }

            if (isPaymentInserted && isCustomerLoanUpdated) {
                TransactionUtil.endTransaction();
                return true;
            } else {
                TransactionUtil.rollBack();
                return false;
            }
        } catch (SQLException e) {
            TransactionUtil.rollBack();
            throw e;
        }
    }

    @Override
    public boolean update(Payment entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT Payment_Date, Loan_Id, Amount FROM Payment WHERE Payment_Id = ?", id);
        if (resultSet.next()) {
            return new Payment(
                    resultSet.getDate("Payment_Date"),
                    resultSet.getInt("Loan_Id"),
                    resultSet.getDouble("Amount")
            );
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<String> getAllStringIds() throws SQLException {
        return null;
    }

    @Override
    public List<Integer> geAllIntegerIds() throws SQLException {
        List<Integer> paymentIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Payment_Id FROM Payment")) {
            while (resultSet.next()) {
                Integer driverId = resultSet.getInt("Payment_Id");
                paymentIds.add(driverId);
            }
        }
        return paymentIds;
    }
}
