package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.dao.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements IService<Customer> {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> list = (List<Customer>)customerRepository.findAll();
        return list;
    }
}
