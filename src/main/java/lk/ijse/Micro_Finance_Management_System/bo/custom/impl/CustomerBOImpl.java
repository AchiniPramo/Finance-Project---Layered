package lk.ijse.Micro_Finance_Management_System.bo.custom.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.CustomerBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.CustomerDAO;
import lk.ijse.Micro_Finance_Management_System.dto.CustomerDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();

        for (Customer customer : all) {
            allCustomer.add(new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getMobileNo()
            ));
        }
        return allCustomer;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getMobileNo()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getName(),
                customerDTO.getAddress(),
                customerDTO.getEmail(),
                customerDTO.getMobileNo()
        ));
    }

    @Override
    public CustomerDTO searchCustomer(String customerId) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(customerId);
        if (customer != null){
            return new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getMobileNo()
            );
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }
}
