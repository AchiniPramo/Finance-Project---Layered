package lk.ijse.Micro_Finance_Management_System.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan {
    private int loanId;
    private String description;
    private double amount;
    private String duration;
    private double interestRate;
}
