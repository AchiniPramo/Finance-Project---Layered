package lk.ijse.Micro_Finance_Management_System.bo.custom;

import lk.ijse.Micro_Finance_Management_System.bo.SuperBO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException;
}
