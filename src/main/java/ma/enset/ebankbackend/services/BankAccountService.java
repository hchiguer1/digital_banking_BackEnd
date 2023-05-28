package ma.enset.ebankbackend.services;

import ma.enset.ebankbackend.dtos.CustomerDTO;
import ma.enset.ebankbackend.entities.BankAccount;
import ma.enset.ebankbackend.entities.CurrentAccount;
import ma.enset.ebankbackend.entities.Customer;
import ma.enset.ebankbackend.entities.SavingAccount;
import ma.enset.ebankbackend.exception.BalanceNotsufficientException;
import ma.enset.ebankbackend.exception.BankAcoountNotFoundException;
import ma.enset.ebankbackend.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException;
    List<CustomerDTO>listCustomer();
    BankAccount getBankAccount(String accountId) throws BankAcoountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAcoountNotFoundException, BalanceNotsufficientException;
    void credit(String accountId, double amount, String description) throws BankAcoountNotFoundException;
    void transfer(String accounIdSource, String accountIdDestination, double amount) throws BankAcoountNotFoundException, BalanceNotsufficientException;
    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long customerID) throws CustomerNotFoundException;

    CustomerDTO UpdateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);
}
