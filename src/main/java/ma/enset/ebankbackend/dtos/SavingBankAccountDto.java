package ma.enset.ebankbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebankbackend.entities.AccountOperation;
import ma.enset.ebankbackend.entities.Customer;
import ma.enset.ebankbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Data
public class SavingBankAccountDto {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;

}
