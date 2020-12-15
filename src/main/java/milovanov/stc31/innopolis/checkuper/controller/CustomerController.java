package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Customer;
import milovanov.stc31.innopolis.checkuper.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {
    final private IService<Customer> service;

    @Autowired
    public CustomerController(IService<Customer> service) {
        this.service = service;
    }

    @GetMapping(value = "/customers")
    public List<Customer> getAllCustomers(Model model) {
        List<Customer> list = service.getAll();
        return list;
    }
}
