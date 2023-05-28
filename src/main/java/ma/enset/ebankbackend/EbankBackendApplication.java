package ma.enset.ebankbackend;

import ma.enset.ebankbackend.dtos.CustomerDTO;
import ma.enset.ebankbackend.entities.*;
import ma.enset.ebankbackend.enums.AccountStatus;
import ma.enset.ebankbackend.enums.OperationType;
import ma.enset.ebankbackend.exception.BalanceNotsufficientException;
import ma.enset.ebankbackend.exception.BankAcoountNotFoundException;
import ma.enset.ebankbackend.exception.CustomerNotFoundException;
import ma.enset.ebankbackend.repositories.AccountOperationRepository;
import ma.enset.ebankbackend.repositories.BankAccountRepository;
import ma.enset.ebankbackend.repositories.CustomerRepository;
import ma.enset.ebankbackend.services.BankAccountService;
import ma.enset.ebankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(EbankBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService/*BankService bankService*/){

            return args -> {
                Stream.of("HAssan","Imane","Mohamed").forEach(name->{
                    CustomerDTO customer=new CustomerDTO();
                    customer.setName(name);
                    customer.setMail(name+"@Gmail.com");
                    bankAccountService.saveCustomer(customer);
                    //bankService.consulter();
                });
                bankAccountService.listCustomer().forEach(customer -> {
                    try {
                        bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                        bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());
                        List<BankAccount >bankAccounts=bankAccountService.bankAccountList();
                                for(BankAccount bankAccount:bankAccounts){
                                    for (int i = 0; i < 10; i++) {
                                        bankAccountService.credit(bankAccount.getId(),10000+Math.random()*120000,"Credit");
                                        bankAccountService.debit(bankAccount.getId(),1000+Math.random()*9000,"Debit");
                                    }
                                }
                    } catch (CustomerNotFoundException e) {
                        e.printStackTrace();
                    }catch (BankAcoountNotFoundException | BalanceNotsufficientException e) {
                        e.printStackTrace();
                    }
                });

        };
    }
    /*//@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("HASSAN","Yassine","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setMail(name+"@gmail.com");
                customerRepository.save(customer);
                    });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(90000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i=0;i<10;i++){
                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*120000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT );
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }*/


}
