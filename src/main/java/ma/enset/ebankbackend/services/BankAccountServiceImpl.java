package ma.enset.ebankbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.ebankbackend.dtos.CustomerDTO;
import ma.enset.ebankbackend.entities.*;
import ma.enset.ebankbackend.enums.OperationType;
import ma.enset.ebankbackend.exception.BalanceNotsufficientException;
import ma.enset.ebankbackend.exception.BankAcoountNotFoundException;
import ma.enset.ebankbackend.exception.CustomerNotFoundException;
import ma.enset.ebankbackend.mappers.BankAccountMapperImp;
import ma.enset.ebankbackend.repositories.AccountOperationRepository;
import ma.enset.ebankbackend.repositories.BankAccountRepository;
import ma.enset.ebankbackend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImp bankAccountMapperImp;
    //Logger log= LoggerFactory.getLogger(this.getClass().getName());//@Slf4j remplace cette ligne -> Lombok

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Custumer");
        Customer customer=bankAccountMapperImp.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return bankAccountMapperImp.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerID).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not Found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentAccount =bankAccountRepository.save(currentAccount);

        return savedCurrentAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerID).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not Found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedSavingAccount =bankAccountRepository.save(savingAccount);
        return savedSavingAccount;
    }


    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDTO>customersDTO=customers.stream()
                .map(customer -> bankAccountMapperImp.fromCustomer(customer))
                .collect(Collectors.toList());
        /*List<CustomerDTO>customersDTO=new ArrayList<>();
        for (Customer customer:customers){
            CustomerDTO customerDTO=bankAccountMapperImp.fromCustomer(customer);
            customersDTO.add(customerDTO);
        }
        return customersDTO;*/
        return customersDTO;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAcoountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAcoountNotFoundException("BankAccount not found"));

        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAcoountNotFoundException, BalanceNotsufficientException {
        BankAccount bankAccount=getBankAccount(accountId);
        if (bankAccount.getBalance()<amount)
            throw new BalanceNotsufficientException("Balance not enough");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAcoountNotFoundException {
        BankAccount bankAccount=getBankAccount(accountId);

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAcoountNotFoundException, BalanceNotsufficientException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);

    }
    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }
    @Override
    public CustomerDTO getCustomer(Long customerID) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerID).
                orElseThrow(()->new CustomerNotFoundException("Customer Not Found"));
        return  bankAccountMapperImp.fromCustomer(customer);
    }
    @Override
    public CustomerDTO UpdateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Custumer");
        Customer customer=bankAccountMapperImp.fromCustomerDTO(customerDTO);
        Customer saveCustomer=customerRepository.save(customer);
        return bankAccountMapperImp.fromCustomer(saveCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

}
