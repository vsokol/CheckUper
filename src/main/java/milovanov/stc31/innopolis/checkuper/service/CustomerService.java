package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements IService<Customer> {
    private CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> list = (List<Customer>) customerDao.findAll();
        return list;
    }
}
