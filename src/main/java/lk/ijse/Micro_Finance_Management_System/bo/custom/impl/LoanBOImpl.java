package lk.ijse.Micro_Finance_Management_System.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.Micro_Finance_Management_System.bo.custom.LoanBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.CollateralDAO;
import lk.ijse.Micro_Finance_Management_System.dao.custom.CustomerDAO;
import lk.ijse.Micro_Finance_Management_System.dao.custom.LoanDAO;
import lk.ijse.Micro_Finance_Management_System.dto.CollateralDTO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.dto.LoanDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Collateral;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.entity.Loan;
import lk.ijse.Micro_Finance_Management_System.util.TransactionUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanBOImpl implements LoanBO {
    private LoanDAO loanDAO = (LoanDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.LOAN);

    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.CUSTOMER);

    private CollateralDAO collateralDAO = (CollateralDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.COLLATERAL);

    @Override
    public ArrayList<LoanDTO> getAllLoan() throws SQLException, ClassNotFoundException {
        ArrayList<LoanDTO> allLoan = new ArrayList<>();
        ArrayList<Loan> all = loanDAO.getAll();

        for (Loan loan : all) {
            allLoan.add(new LoanDTO(
                    loan.getLoanId(),
                    loan.getDescription(),
                    loan.getAmount(),
                    loan.getDuration(),
                    loan.getInterestRate()
            ));
        }
        return allLoan;
    }

    @Override
    public List<Integer> getAllLoanIds() throws SQLException {
        return loanDAO.geAllIntegerIds();
    }

    @Override
    public boolean placeLoan(LoanDTO loanDTO, String customerId, String collateralId) throws SQLException {
        try {
            TransactionUtil.startTransaction();

            // Insert into Loan table
            Loan loan = new Loan(loanDTO.getLoanId(), loanDTO.getDescription(), loanDTO.getAmount(), loanDTO.getDuration(), loanDTO.getInterestRate());
            boolean isLoanInserted = loanDAO.save(loan);
            if (!isLoanInserted) {
                TransactionUtil.rollBack();
                return false;
            }

            // Get the inserted Loan ID
            int loanId = loanDAO.getInsertedLoanId();

            // Calculate total amount to pay
            double totalAmountToPay = loanDAO.calculateTotalAmountToPay(loanDTO.getAmount(), loanDTO.getInterestRate(), Integer.parseInt(loanDTO.getDuration()));

            boolean isOverdue = loanDAO.checkIfOverdue(loanId);

            if (isOverdue) {
                double penaltyAmount = loanDAO.calculatePenaltyAmount(totalAmountToPay, loanId);
                loanDAO.addPenaltyIfOverdue(loanId, penaltyAmount);
                totalAmountToPay += penaltyAmount;
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate dueDate = currentDate.plusDays(Integer.parseInt(loanDTO.getDuration()));
            String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedDueDate = dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Insert into Customer_Loan table
            boolean isCustomerLoanInserted = loanDAO.insertCustomerLoan(loanId, customerId, formattedCurrentDate, formattedDueDate, totalAmountToPay);

            if (!isCustomerLoanInserted) {
                TransactionUtil.rollBack();
                return false;
            }

            boolean isCollateralUpdated = collateralDAO.updateCollateralLoan(collateralId, loanId);

            if (isCustomerLoanInserted && isCollateralUpdated) {
                TransactionUtil.endTransaction();
                return true;

            } else {
                TransactionUtil.rollBack();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            TransactionUtil.rollBack();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            return false;
        }
    }
    @Override
    public CustomerDTO getCustomer(String customerId) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(customerId);
        if (customer != null) {
            return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getEmail(), customer.getMobileNo());
        }
        return null;
    }

    @Override
    public CollateralDTO getCollateral(String collateralId) throws SQLException, ClassNotFoundException {
        Collateral collateral = collateralDAO.search(collateralId);
        if (collateral != null) {
            return new CollateralDTO(collateral.getCollateralId(), collateral.getName(), collateral.getAddress(), collateral.getPhoneNumber());
        }
        return null;
    }
}