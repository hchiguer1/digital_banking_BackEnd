package ma.enset.ebankbackend.services;

import ma.enset.ebankbackend.entities.BankAccount;
import ma.enset.ebankbackend.entities.CurrentAccount;
import ma.enset.ebankbackend.entities.SavingAccount;
import ma.enset.ebankbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount =
                bankAccountRepository.findById("6dee99e3-484f-4e84-be85-baed703c11a6").orElse(null);
        if (bankAccount != null) {
            System.out.println("------------------------------");
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getClass().getSimpleName());
            System.out.println(bankAccount.getCustomer().getId());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getCustomer().getMail());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println(((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println(((SavingAccount) bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperationList().forEach(op -> {
                System.out.println("----------------------");
                System.out.println(op.getType()+"\t"+op.getOperationDate()+"\t"+op.getAmount());
            });
        }
        ;
    }
}
