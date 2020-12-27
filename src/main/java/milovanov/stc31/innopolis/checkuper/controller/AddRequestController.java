package milovanov.stc31.innopolis.checkuper.controller;

import milovanov.stc31.innopolis.checkuper.pojo.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddRequestController {

    @GetMapping(value = "/addrequest")
    public ModelAndView addRequest() {
        ModelAndView modelAndView = null;
        return null;
    }
}
