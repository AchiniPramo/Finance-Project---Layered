package lk.ijse.Micro_Finance_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDTO {
    private int paymentId;
    private Date paymentDate;
    private int loanId;
    private double amount;
}
