package lk.ijse.Micro_Finance_Management_System.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Collateral {
    private String collateralId;
    private String name;
    private String address;
    private String phoneNumber;
}
