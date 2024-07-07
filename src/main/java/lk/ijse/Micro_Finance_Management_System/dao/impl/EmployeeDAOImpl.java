package lk.ijse.Micro_Finance_Management_System.dao.impl;

import lk.ijse.Micro_Finance_Management_System.dao.custom.EmployeeDAO;
import lk.ijse.Micro_Finance_Management_System.entity.Customer;
import lk.ijse.Micro_Finance_Management_System.entity.Employee;
import lk.ijse.Micro_Finance_Management_System.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployee = new ArrayList<>();
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM Employee");
        while (resultSet.next()) {
            Employee employee = new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            allEmployee.add(employee);
        }
        return allEmployee;
    }

    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO Employee (Employee_Id, Name, Address, Salary, Phone_Number, Email) VALUES (?,?,?,?,?,?)",
                entity.getEmployeeId(),
                entity.getName(),
                entity.getAddress(),
                entity.getSalary(),
                entity.getPhoneNumber(),
                entity.getEmail());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("UPDATE Employee SET Name = ?, Address = ?, Salary = ?, Phone_Number = ?, Email = ? WHERE Employee_Id = ?",
                entity.getName(),
                entity.getAddress(),
                entity.getSalary(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getEmployeeId()
        );
    }
    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT  Employee_Id, Name, Address, Salary, Phone_Number, Email FROM Employee WHERE Employee_Id = ?", id);

        if (resultSet.next()) {
            return new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("DELETE FROM Employee WHERE Employee_Id = ?", id);
    }

    @Override
    public List<String> getAllStringIds() throws SQLException {
        List<String> employeeIds = new ArrayList<>();

        try (ResultSet resultSet = SQLUtil.sql("SELECT Employee_Id FROM Employee")) {
            while (resultSet.next()) {
                String employeeId = resultSet.getString("Employee_Id");
                employeeIds.add(employeeId);
            }
        }
        return employeeIds;
    }

    @Override
    public List<Integer> geAllIntegerIds() throws SQLException {
        return null;
    }
}