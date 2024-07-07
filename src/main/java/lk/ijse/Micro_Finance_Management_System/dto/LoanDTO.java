package lk.ijse.Micro_Finance_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoanDTO {
    private int loanId;
    private String description;
    private double amount;
    private String duration;
    private double interestRate;
}
