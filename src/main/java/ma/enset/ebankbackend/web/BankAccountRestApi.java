package ma.enset.ebankbackend.web;

import lombok.AllArgsConstructor;
import ma.enset.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BankAccountRestApi {
    private BankAccountService bankAccountService;

}
