package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RequestController {
    final private IService<Request> service;

    @Autowired
    public RequestController(IService<Request> service) {
        this.service = service;
    }

    @GetMapping(value = "/requests")
    public List<Request> getAllRequests(Model model) {
        List<Request> list = service.getAll();
        model.addAttribute("requests", list);

        return list;
    }
}
