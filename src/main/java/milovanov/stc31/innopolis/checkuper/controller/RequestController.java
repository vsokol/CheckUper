package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Request;
import milovanov.stc31.innopolis.checkuper.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RequestController {
    final private IService<Request> service;

    @Autowired
    public RequestController(IService<Request> service) {
        this.service = service;
    }

    @GetMapping(value = "/requests")
    public ModelAndView getAllRequests() {
        List<Request> list = service.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("requests", list);
        modelAndView.setViewName("allrequests");
        return modelAndView;
    }
}
