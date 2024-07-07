package lk.ijse.Micro_Finance_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private String customerId;
    private String name;
    private String address;
    private String email;
    private String mobileNo;
}
