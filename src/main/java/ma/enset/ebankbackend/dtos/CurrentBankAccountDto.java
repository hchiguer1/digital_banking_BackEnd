package ma.enset.ebankbackend.dtos;

import lombok.Data;
import ma.enset.ebankbackend.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentBankAccountDto {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;

}
