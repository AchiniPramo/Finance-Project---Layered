package lk.ijse.Micro_Finance_Management_System.bo.custom.impl;

import lk.ijse.Micro_Finance_Management_System.bo.custom.EmployeeBO;
import lk.ijse.Micro_Finance_Management_System.dao.DAOFactory;
import lk.ijse.Micro_Finance_Management_System.dao.custom.EmployeeDAO;
import lk.ijse.Micro_Finance_Management_System.dto.EmployeeDTO;
import lk.ijse.Micro_Finance_Management_System.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    private EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDAOFactory().getDAOType(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmployee = new ArrayList<>();
        ArrayList<Employee> all = employeeDAO.getAll();

        for (Employee employee : all) {
            allEmployee.add(new EmployeeDTO(
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getSalary(),
                    employee.getPhoneNumber(),
                    employee.getEmail()
            ));
        }
        return allEmployee;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getAddress(),
                employeeDTO.getSalary(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getEmail()
        ));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(
                employeeDTO.getEmployeeId(),
                employeeDTO.getName(),
                employeeDTO.getAddress(),
                employeeDTO.getSalary(),
                employeeDTO.getPhoneNumber(),
                employeeDTO.getEmail()
        ));
    }

    @Override
    public EmployeeDTO searchEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(employeeId);
        if (employee != null) {
            return new EmployeeDTO(
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getSalary(),
                    employee.getPhoneNumber(),
                    employee.getEmail()
            );
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public List<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllStringIds();
    }
}
