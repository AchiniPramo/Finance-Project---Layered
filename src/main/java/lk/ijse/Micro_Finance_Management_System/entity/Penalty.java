package lk.ijse.Micro_Finance_Management_System.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Penalty {
    private int penaltyId;
    private String reason;
    private int loanId;
    private double amount;
    private LocalDate dateApplied;
}
