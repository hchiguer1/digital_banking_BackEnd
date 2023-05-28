package ma.enset.ebankbackend.mappers;

import ma.enset.ebankbackend.dtos.CurrentBankAccountDto;
import ma.enset.ebankbackend.dtos.CustomerDTO;
import ma.enset.ebankbackend.dtos.SavingBankAccountDto;
import ma.enset.ebankbackend.entities.CurrentAccount;
import ma.enset.ebankbackend.entities.Customer;
import ma.enset.ebankbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImp {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        //customerDTO.setId(customer.getId());
        //customerDTO.setMail(customer.getMail());
        //customerDTO.setName(customer.getName());
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingBankAccountDto fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDto savingBankAccountDto=new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDto);
        savingBankAccountDto.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        return savingBankAccountDto;
    }
    public SavingAccount fromSavingBankAccountDto(SavingBankAccountDto savingAccountDto){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDto.getCustomerDTO()));
        return savingAccount;
    }
    public CurrentBankAccountDto  fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBankAccountDto currentBankAccountDto=new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
        currentBankAccountDto.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        return currentBankAccountDto;
    }
    public CurrentAccount fromCurrentBankAccountDto(CurrentBankAccountDto currentBankAccountDto){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDto.getCustomerDTO()));
        return currentAccount;
    }

}
