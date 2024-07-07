package lk.ijse.Micro_Finance_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ExpenseDTO {
    private int expenseId;
    private String type;
    private Date date;
    private String employeeId;
    private double amount;
}
